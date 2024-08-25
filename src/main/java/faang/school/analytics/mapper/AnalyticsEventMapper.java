package faang.school.analytics.mapper;

import faang.school.analytics.dto.AnalyticsEventDto;
import faang.school.analytics.event.MentorshipRequestEvent;
import faang.school.analytics.model.AnalyticsEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnalyticsEventMapper {

    AnalyticsEvent toEntity(AnalyticsEventDto analyticsEventDto);

    AnalyticsEventDto toDto(AnalyticsEvent analyticsEvent);

    @Mapping(target = "eventType", constant = "MENTORSHIP_REQUESTED")
    @Mapping(target = "actorId", source = "requesterId")
    @Mapping(target = "receivedAt", source = "requestedAt")
    AnalyticsEvent toAnalyticsEvent(MentorshipRequestEvent event);
}
