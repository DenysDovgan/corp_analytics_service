package faang.school.analytics.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.analytics.config.TestContainersConfig;
import faang.school.analytics.dto.PostViewEventDto;
import faang.school.analytics.model.AnalyticsEvent;
import faang.school.analytics.model.EventType;
import faang.school.analytics.repository.AnalyticsEventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@ContextConfiguration(classes = TestContainersConfig.class)
public class PostViewEventIntegrationTest {
    private static final long AWAIT_TIMEOUT_SECONDS = 5;
    private static final String REDIS_CHANNEL_POST_VIEW = "spring.data.redis.channel.post-view";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AnalyticsEventRepository analyticsEventRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Environment environment;

    @AfterEach
    public void cleanUp() {
        analyticsEventRepository.deleteAll();
        TestContainersConfig.redisContainer.stop();
    }

    @Test
    public void testPostViewEventProcessing() throws Exception {
        String postViewEventChannel = environment.getProperty(REDIS_CHANNEL_POST_VIEW);

        PostViewEventDto postViewEvent = new PostViewEventDto(1L, 2L, 3L, LocalDateTime.now());
        String message = objectMapper.writeValueAsString(postViewEvent);

        redisTemplate.convertAndSend(Objects.requireNonNull(postViewEventChannel), message);

        await().atMost(AWAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS).untilAsserted(() -> {
            List<AnalyticsEvent> events = analyticsEventRepository.findAll();
            assertThat(events).hasSize(1);

            AnalyticsEvent event = events.get(0);
            assertThat(event.getReceiverId()).isEqualTo(postViewEvent.getReceiverId());
            assertThat(event.getActorId()).isEqualTo(postViewEvent.getActorId());
            assertThat(event.getEventType()).isEqualTo(EventType.POST_VIEW);
        });
    }
}
