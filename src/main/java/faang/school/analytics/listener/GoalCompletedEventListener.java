package faang.school.analytics.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.analytics.dto.GoalCompletedEvent;
import faang.school.analytics.mapper.AnalyticsEventMapper;
import faang.school.analytics.model.AnalyticsEvent;
import faang.school.analytics.service.AnalyticsEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class GoalCompletedEventListener implements MessageListener {
    private final ObjectMapper objectMapper;
    private final AnalyticsEventService analyticsEventService;
    private final AnalyticsEventMapper analyticsEventMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            GoalCompletedEvent goalCompletedEvent = objectMapper.readValue(message.getBody(), GoalCompletedEvent.class);
            AnalyticsEvent analyticsEvent = analyticsEventMapper.toEntity(goalCompletedEvent);

            log.info("Goal completed event received: {}", analyticsEvent);
            analyticsEventService.saveEvent(analyticsEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
