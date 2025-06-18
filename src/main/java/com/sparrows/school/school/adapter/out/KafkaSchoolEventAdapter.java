package com.sparrows.school.school.adapter.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrows.school.kafka.outbox.OutboxEvent;
import com.sparrows.school.kafka.outbox.OutboxEventRepository;
import com.sparrows.school.kafka.payload.school.SchoolCreatedPayload;
import com.sparrows.school.kafka.payload.school.SchoolEventEnum;
import com.sparrows.school.kafka.properties.KafkaProperties;
import com.sparrows.school.school.port.out.SchoolEventPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@AllArgsConstructor
public class KafkaSchoolEventAdapter implements SchoolEventPort {
    private final KafkaProperties kafkaProperties;
    private final ObjectMapper objectMapper;
    private final OutboxEventRepository outboxEventRepository;

    @Transactional
    public void publishSchoolCreatedEvent(Integer schoolId, String schoolName) {
        SchoolCreatedPayload payload = new SchoolCreatedPayload();
        payload.setSchoolId(schoolId);
        payload.setSchoolName(schoolName);
        String json = null;
        try {
            json = objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 직렬화 실패", e);
        }

        OutboxEvent outboxEvent = new OutboxEvent();
        outboxEvent.setAggregateType(kafkaProperties.getAggregateType().getSchool());
        outboxEvent.setAggregateId(String.valueOf(schoolId));
        outboxEvent.setEventType(SchoolEventEnum.SchoolCreated.toString());
        outboxEvent.setTopic(kafkaProperties.getTopic().getSchool().getCreate());
        outboxEvent.setKey(String.valueOf(schoolId));
        outboxEvent.setPayload(json); // Object → JSON 문자열
        outboxEvent.setStatus("PENDING");

        outboxEventRepository.save(outboxEvent);
    }
}
