package faang.school.analytics.service;

import faang.school.analytics.dto.RecommendationEvent;
import faang.school.analytics.mapper.AnalyticsEventMapper;
import faang.school.analytics.model.AnalyticsEvent;
import faang.school.analytics.model.LikeEvent;
import faang.school.analytics.repository.AnalyticsEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static faang.school.analytics.model.EventType.POST_LIKE;
import static faang.school.analytics.model.EventType.RECOMMENDATION_RECEIVED;

@Service
@RequiredArgsConstructor
public class AnalyticsEventServiceImpl implements AnalyticsEventService {

    private final AnalyticsEventRepository analyticsEventRepository;
    private final AnalyticsEventMapper analyticsEventMapper;

    @Override
    public void saveLikeEvent(LikeEvent likeEvent) {
        AnalyticsEvent analyticsEvent = analyticsEventMapper.toEntity(likeEvent);
        analyticsEvent.setEventType(POST_LIKE);
        analyticsEventRepository.save(analyticsEvent);
    }

    @Override
    public void saveRecommendationEvent(RecommendationEvent recommendationEvent) {
        AnalyticsEvent analyticsEvent = analyticsEventMapper.toEntity(recommendationEvent);
        analyticsEvent.setEventType(RECOMMENDATION_RECEIVED);
        analyticsEventRepository.save(analyticsEvent);
    }
}