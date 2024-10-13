package faang.school.analytics.mapper;

import faang.school.analytics.dto.message.AnalyticsEventDto;
import faang.school.analytics.dto.message.MentorshipRequestMessage;
import faang.school.analytics.model.AnalyticsEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MentorshipRequestMapper {

    @Mapping(source = "requester", target = "actorId")
    @Mapping(source = "createdAt", target = "receivedAt")
    AnalyticsEvent toAnalyticsEvent(MentorshipRequestMessage mentorshipRequestMessage);

    AnalyticsEventDto toAnalyticsEventDto(AnalyticsEvent analyticsEvent);
}
