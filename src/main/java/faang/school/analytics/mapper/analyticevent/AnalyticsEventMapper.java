package faang.school.analytics.mapper.analyticevent;

import faang.school.analytics.model.dto.FollowerEventDto;
import faang.school.analytics.model.dto.LikeEventDto;
import faang.school.analytics.model.dto.RecommendationEventDto;
import faang.school.analytics.model.dto.analyticsevent.AnalyticsEventDto;
import faang.school.analytics.model.entity.AnalyticsEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnalyticsEventMapper {

    AnalyticsEventDto toDto(AnalyticsEvent analyticsEvent);

    @Mapping(target = "receiverId", source = "followeeId")
    @Mapping(target = "actorId", source = "followerId")
    @Mapping(target = "receivedAt", source = "subscribedAt")
    AnalyticsEvent toEntity(FollowerEventDto followerEvent);

    @Mapping(target = "receiverId", source = "postId")
    @Mapping(target = "actorId", source = "userId")
    @Mapping(target = "receivedAt", source = "likedTime")
    AnalyticsEvent toEntity(LikeEventDto likeEventDto);

    @Mapping(target = "receiverId", source = "receiverId")
    @Mapping(target = "actorId", source = "authorId")
    @Mapping(target = "receivedAt", source = "recommendedAt")
    AnalyticsEvent toEntity(RecommendationEventDto recommendationEventDto);
}