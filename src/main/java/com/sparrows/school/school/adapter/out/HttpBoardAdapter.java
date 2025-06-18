package com.sparrows.school.school.adapter.out;

import com.sparrows.school.school.exception.handling.FailBoardApiIntegrationException;
import com.sparrows.school.school.model.dto.CreateBoardRequestDto;
import com.sparrows.school.school.port.out.BoardPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class HttpBoardAdapter implements BoardPort {

    private final RestTemplate restTemplate;

    public HttpBoardAdapter() {
        this.restTemplate = new RestTemplate();
    }

    private static final String BOARD_API_URL = "http://localhost:8080/board"; // TODO: 운영 주소로 교체

    public void sendCreateBoardRequest(CreateBoardRequestDto request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<CreateBoardRequestDto> entity = new HttpEntity<>(request, headers);
            restTemplate.postForEntity(BOARD_API_URL, entity, Void.class);

            log.info("✅ 게시판 생성 요청 성공 - 학교 ID: {}, 이름: {}", request.getSchoolId(), request.getName());
        } catch (Exception e) {
            log.error("📛 게시판 생성 API 요청 실패 - 학교 ID: {}, 이름: {}", request.getSchoolId(), request.getName(), e);
            throw new FailBoardApiIntegrationException();
        }
    }
}
