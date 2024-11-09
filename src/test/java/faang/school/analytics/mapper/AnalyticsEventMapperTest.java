package faang.school.analytics.mapper;

import faang.school.analytics.dto.MentorshipRequestReceivedDto;
import faang.school.analytics.dto.PostViewEventDto;
import faang.school.analytics.dto.user.premium.PremiumBoughtEventDto;
import faang.school.analytics.model.AnalyticsEvent;
import faang.school.analytics.model.EventType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static faang.school.analytics.model.EventType.MENTORSHIP_REQUEST_RECEIVED;
import static faang.school.analytics.model.EventType.POST_VIEW;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AnalyticsEventMapperTest {
    private static final long USER_ID = 1L;
    private static final double COST = 10.0;
    private static final int DAYS = 31;
    private static final EventType EVENT_TYPE = EventType.USER_PREMIUM_BOUGHT;
    private static final LocalDateTime PURCHASE_DATE = LocalDateTime.of(2000, 1, 1, 1, 1);

    private final AnalyticsEventMapper mapper = Mappers.getMapper(AnalyticsEventMapper.class);

    @Test
    void postViewEventDtoToAnalyticsEvent() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 10, 11, 12, 13, 15);
        PostViewEventDto dto = new PostViewEventDto(1L, 2L, 3L, dateTime);
        AnalyticsEvent expected = new AnalyticsEvent(0L, 2L, 3L, POST_VIEW, dateTime);
        AnalyticsEvent event = mapper.postViewEventDtoToAnalyticsEvent(dto);

        assertThat(expected)
                .usingRecursiveComparison()
                .isEqualTo(event);
    }

    @Test
    @DisplayName("Mapping PremiumBoughtEventDto to AnalyticsEvent successful")
    void testPremiumBoughtEventDtoToAnalyticsEventSuccessful() {
        PremiumBoughtEventDto dto = new PremiumBoughtEventDto(USER_ID, COST, DAYS, PURCHASE_DATE);

        AnalyticsEvent analyticsEvent = mapper.toAnalyticsEvent(dto);

        assertThat(analyticsEvent.getReceiverId()).isEqualTo(USER_ID);
        assertThat(analyticsEvent.getActorId()).isEqualTo(USER_ID);
        assertThat(analyticsEvent.getEventType()).isEqualTo(EVENT_TYPE);
        assertThat(analyticsEvent.getReceivedAt()).isEqualTo(PURCHASE_DATE);
    }

    @Test
    void mentorshipRequestReceivedDtoToAnalyticsEvent() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 10, 11, 12, 13, 15);
        MentorshipRequestReceivedDto dto = new MentorshipRequestReceivedDto(1L, 2L, 3L, dateTime);
        AnalyticsEvent expected = new AnalyticsEvent(0L, 2L, 3L, MENTORSHIP_REQUEST_RECEIVED, dateTime);
        AnalyticsEvent event = mapper.mentorshipRequestReceivedDtoToAnalyticsEvent(dto);
        assertEquals(expected, event);
    }
}