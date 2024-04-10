package faang.school.analytics.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.analytics.dto.AnalyticsEventDto;
import faang.school.analytics.mapper.AnalyticsEventMapper;
import faang.school.analytics.model.EventType;
import faang.school.analytics.service.AnalyticsEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProjectViewEventListener implements MessageListener {
    @Async
    @Override
    public void onMessage(Message message, byte[] pattern) {
        Map<String, String> data;
        try {
            String jsonMessage = new String((byte[]) message.getBody());
             data = new ObjectMapper().readValue(jsonMessage, Map.class);
        } catch (JsonProcessingException e) {
            log.error("Error parsing JSON message: {}", e.getMessage());
            return;
        }

        String userId = (String) data.get("userId");
        String projectId = (String) data.get("projectId");
        String timestamp = data.get("timestamp");

        log.info("Received ProjectViewEvent: userId={}, projectId={}, timestamp={}", userId, projectId, timestamp);

        try {
            AnalyticsEventDto eventDto = new AnalyticsEventDto();
            eventDto.setReceiverId(Long.parseLong(projectId));
            eventDto.setActorId(Long.parseLong(userId));
            eventDto.setEventType(EventType.PROJECT_VIEW);
            eventDto.setReceivedAt(LocalDateTime.parse(timestamp));

            AnalyticsEventService analyticsEventService

        } catch (Exception e) {
            log.error("Error processing ProjectViewEvent: {}", e.getMessage());
            throw e;
        }
    }
}
