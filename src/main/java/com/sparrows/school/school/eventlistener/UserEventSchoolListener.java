package com.sparrows.school.school.eventlistener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrows.school.kafka.payload.user.UserCreatedPayload;
import com.sparrows.school.kafka.payload.user.UserEventEnum;
import com.sparrows.school.kafka.payload.user.UserType;
import com.sparrows.school.school.port.in.UserSchoolUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserEventSchoolListener {

    @Autowired
    UserSchoolUseCase userSchoolUseCase;

    @Autowired
    ObjectMapper objectMapper;


    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 2000, multiplier = 2),
            dltTopicSuffix = ".dlt",
            autoCreateTopics = "true"
    )
    @KafkaListener(topics = "${kafka.topic.user.create}", groupId = "${kafka.groupId.school}")
    public void handleUserEvent(String message) throws JsonProcessingException {
        System.out.println(message);
        //String json = objectMapper.readValue(message, String.class);  // 첫 번째 언래핑
        UserCreatedPayload createdPayload = objectMapper.readValue(message,UserCreatedPayload.class);
        if (createdPayload.getUserType() == UserType.OFFICIAL) handleUserCreated(createdPayload);
    }

    @KafkaListener(topics = "${kafka.topic.user.create}.dlt", groupId = "${kafka.groupId.school}")
    public void handleDlt(String message) {
        log.error("DLT 메시지 수신: {}", message);
        // 저장, 알림, 재처리 로직 등
    }

    private UserEventEnum parseEventType(String type) {
        try {
            return UserEventEnum.valueOf(type);
        } catch (IllegalArgumentException | NullPointerException e) {
            log.warn("Unsupported or malformed user event type: {}", type);
            return null;
        }
    }

    private void handleUserCreated(UserCreatedPayload payload) {
        userSchoolUseCase.addInitializeUserSchoolRelation(payload.getUserId(), payload.getSchoolId());
    }

    private void handleUserUpdated(String message) {
        //UserCreatedPayload payload = (UserCreatedPayload) event.getPayload();
        //userSchoolUseCase.addAdditionalSchoolRelation(event.getUserId(), payload.getSchoolId());
    }

}
