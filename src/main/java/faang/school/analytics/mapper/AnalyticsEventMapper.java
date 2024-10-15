package faang.school.analytics.mapper;

import faang.school.analytics.model.dto.AdBoughtEvent;
import faang.school.analytics.model.dto.ProfileViewEvent;
import faang.school.analytics.model.dto.SearchAppearanceEvent;
import faang.school.analytics.model.entity.AnalyticsEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnalyticsEventMapper {

    AnalyticsEvent fromSearchAppearanceToEntity(SearchAppearanceEvent searchAppearanceEvent);

    AnalyticsEvent fromProfileViewToEntity(ProfileViewEvent profileViewEvent);

    @Mapping(source = "userId", target = "actorId")
    AnalyticsEvent fromAdBoughtToEntity(AdBoughtEvent adBoughtEvent);
}
