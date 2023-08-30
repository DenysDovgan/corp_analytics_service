package faang.school.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequestEventDto {

    private Long recommendationId;

    private Long actorId;

    private LocalDateTime receivedAt;
}