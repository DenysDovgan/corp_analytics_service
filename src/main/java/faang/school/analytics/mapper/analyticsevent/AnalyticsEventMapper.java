package faang.school.analytics.mapper.analyticsevent;

import faang.school.analytics.model.dto.AnalyticsEventDto;
import faang.school.analytics.model.entity.AnalyticsEvent;
import faang.school.analytics.model.event.SearchAppearance.SearchAppearanceEvent;
import faang.school.analytics.model.event.AdBoughtEvent;
import faang.school.analytics.model.event.CommentEvent;
import faang.school.analytics.model.event.FollowerEvent;
import faang.school.analytics.model.event.FundRaisedEvent;
import faang.school.analytics.model.event.GoalCompletedEvent;
import faang.school.analytics.model.event.LikeEvent;
import faang.school.analytics.model.event.MentorshipRequestedEvent;
import faang.school.analytics.model.event.PostViewEvent;
import faang.school.analytics.model.event.PremiumBoughtEvent;
import faang.school.analytics.model.event.ProjectViewEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnalyticsEventMapper {

    AnalyticsEventDto toDto(AnalyticsEvent analyticsEvent);

    @Mapping(target = "receiverId", source = "followeeId")
    @Mapping(target = "actorId", source = "followerId")
    @Mapping(target = "receivedAt", source = "subscribedAt")
    AnalyticsEvent toEntity(FollowerEvent followerEvent);

    @Mapping(target = "receiverId", source = "postAuthorId")
    @Mapping(target = "actorId", source = "userId")
    @Mapping(target = "receivedAt", source = "likedTime")
    AnalyticsEvent toEntity(LikeEvent likeEvent);

    @Mapping(target = "receiverId", source = "commentId")
    @Mapping(target = "actorId", source = "authorId")
    @Mapping(target = "receivedAt", source = "commentedAt")
    AnalyticsEvent toEntity(CommentEvent commentEvent);

    @Mapping(target = "receiverId", source = "goalId")
    @Mapping(target = "actorId", source = "userId")
    @Mapping(target = "receivedAt", source = "completedAt")
    AnalyticsEvent toEntity(GoalCompletedEvent goalCompletedEvent);

    @Mapping(target = "receiverId", source = "projectId")
    @Mapping(target = "actorId", source = "userId")
    @Mapping(target = "receivedAt", source = "visitTime")
    AnalyticsEvent toEntity(ProjectViewEvent event);

    @Mapping(source = "userId", target = "actorId")
    @Mapping(source = "requestedAt", target = "receivedAt")
    AnalyticsEvent toEntity(MentorshipRequestedEvent mentorshipRequestedEvent);

    @Mapping(target = "actorId", source = "userId")
    @Mapping(target = "receivedAt", source = "observeTime")
    AnalyticsEvent toEntity(PremiumBoughtEvent premiumBoughtEvent);

    @Mapping(target = "receiverId", source = "foundUserId")
    @Mapping(target = "actorId", source = "requesterId")
    @Mapping(target = "receivedAt", source = "requestDateTime")
    AnalyticsEvent toEntity(SearchAppearanceEvent event);

    @Mapping(target = "actorId", source = "authorPostId")
    @Mapping(target = "receiverId", source = "postId")
    @Mapping(target = "receivedAt", source = "viewTime")
    AnalyticsEvent toEntity(PostViewEvent postViewEvent);

    @Mapping(source = "userId", target = "actorId")
    @Mapping(source = "donatedAt", target = "receivedAt")
    AnalyticsEvent toEntity(FundRaisedEvent fundRaisedEvent);

    @Mapping(target = "receiverId", source = "postId")
    @Mapping(target = "actorId", source = "userId")
    @Mapping(target = "receivedAt", source = "boughtAt")
    AnalyticsEvent toEntity(AdBoughtEvent adBoughtEvent);
}