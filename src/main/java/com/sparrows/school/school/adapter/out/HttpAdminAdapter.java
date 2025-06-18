package com.sparrows.school.school.adapter.out;

import com.sparrows.school.school.exception.handling.FailAdminApiIntegrationException;
import com.sparrows.school.school.model.dto.CreateFailureRequestDto;
import com.sparrows.school.school.port.out.AdminPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class HttpAdminAdapter implements AdminPort {

    private final RestTemplate restTemplate;

    public HttpAdminAdapter() {
        this.restTemplate = new RestTemplate();
    }

    private static final String BOARD_API_URL = "http://localhost:8080/admin";

    public void sendCreateFailureRequest(CreateFailureRequestDto request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<CreateFailureRequestDto> entity = new HttpEntity<>(request, headers);
            restTemplate.postForEntity(BOARD_API_URL + "/failure", entity, Void.class);

            log.info("✅ 실패 보고 요청 성공 - 사유: {}", request.getReason());
        } catch (Exception e) {
            log.error("📛 실패 보고 API 요청 실패 - 사유: {}", request.getReason(), e);
            throw new FailAdminApiIntegrationException();
        }
    }
}
