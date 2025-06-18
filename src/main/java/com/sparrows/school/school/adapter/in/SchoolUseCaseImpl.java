package com.sparrows.school.school.adapter.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrows.school.school.exception.SchoolErrorCode;
import com.sparrows.school.school.model.dto.CreateFailureRequestDto;
import com.sparrows.school.school.model.dto.internal.SchoolSaveRequest;
import com.sparrows.school.school.model.dto.internal.SchoolSearchRequest;
import com.sparrows.school.school.model.entity.SchoolEntity;
import com.sparrows.school.school.port.in.SchoolUseCase;
import com.sparrows.school.school.port.out.AdminPort;
import com.sparrows.school.school.port.out.SchoolRepositoryPort;
import com.sparrows.school.school.port.out.SchoolSearchPort;
import com.sparrows.school.school.model.dto.internal.KakaoGeoResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SchoolUseCaseImpl implements SchoolUseCase {
    @Value("${neis.url}")
    private String baseUrl;

    @Value("${neis.api-key}")
    private String apiKey;

    @Value("${kakao.api-key}")
    private String kakaoKey;

    private final SchoolRepositoryPort schoolRepositoryPort;
    private final AdminPort adminPort;
    private final SchoolSearchPort schoolSearchPort;

    public SchoolUseCaseImpl(SchoolSearchPort schoolSearchPort, SchoolRepositoryPort schoolRepositoryPort, AdminPort adminPort) {
        this.schoolRepositoryPort = schoolRepositoryPort;
        this.schoolSearchPort = schoolSearchPort;
        this.adminPort = adminPort;
    }

    // 내 위치가 학교의 위치랑 같은지 여부 반환
    @Override
    public boolean isSameLocationWithSchoolId(int schoolId, double latitude, double longitude) {

        try {
            String jsonResponse = getAddressFromCoordinates(latitude, longitude);
            KakaoGeoResponseDto kakaoGeoResponseDto = new ObjectMapper().readValue(jsonResponse, KakaoGeoResponseDto.class);

            List<KakaoGeoResponseDto.Document> documents = kakaoGeoResponseDto.getDocuments();
            if (documents == null || documents.isEmpty()) {
                log.warn("📭 주소 조회 결과 없음 (위도: {}, 경도: {})", latitude, longitude);
                adminPort.sendCreateFailureRequest(CreateFailureRequestDto.from(SchoolErrorCode.FAIL_LOCATION_NOT_FOUND));
                return false;
            }

            String addressName = documents.getFirst().getRoadAddress().getAddressName();
            log.info("📍 조회된 도로명 주소: {}", addressName);

            Optional<SchoolEntity> optionalSchool = schoolRepositoryPort.findSchoolById(schoolId);
            if (optionalSchool.isEmpty()) {
                log.warn("🏫 학교 ID({})에 해당하는 학교 정보 없음", schoolId);
                adminPort.sendCreateFailureRequest(CreateFailureRequestDto.from(SchoolErrorCode.SCHOOL_NOT_FOUND));
                return false;
            }

            String registeredAddress = optionalSchool.get().getAddress();
            boolean isMatch = registeredAddress.equals(addressName);
            log.info("✅ 주소 일치 여부: {}", isMatch);
            return isMatch;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // 위도 경도로 도로명 주소 반환
    public String getAddressFromCoordinates(double latitude, double longitude) {
        RestTemplate restTemplate = new RestTemplate();

        // URL 파라미터 추가
        String url = UriComponentsBuilder.fromHttpUrl("https://dapi.kakao.com/v2/local/geo/coord2address.json")
                .queryParam("x", longitude)
                .queryParam("y", latitude)
                .toUriString();

        // HTTP 헤더 설정 (Authorization 포함)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청 생성
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // API 요청 실행 및 응답 받기
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody(); // 응답 JSON 반환
    }

    @Override
    public List<SchoolEntity> getSchoolByInitialConsonant(String query) {
        List<Integer> ids = schoolSearchPort.search(SchoolSearchRequest.from(query)).getIds();

        List<SchoolEntity> schools = new ArrayList<>();
        for(Integer id: ids){
            schools.add(schoolRepositoryPort.findSchoolById(id).orElseThrow());
        }

        return schools;
    }

    @Override
    public void insertSchool(SchoolEntity school) {
        schoolRepositoryPort.save(school);
        schoolSearchPort.save(SchoolSaveRequest.from(school));
    }
}
