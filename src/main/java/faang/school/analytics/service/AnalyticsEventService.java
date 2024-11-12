package faang.school.analytics.service;

import faang.school.analytics.dto.AnalyticsEventDto;
import faang.school.analytics.mapper.AnalyticsEventMapper;
import faang.school.analytics.repository.AnalyticsEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsEventService {

    private final AnalyticsEventRepository analyticsEventRepository;
    private final AnalyticsEventMapper analyticsEventMapper;

    public ResponseEntity<Void> saveAction(AnalyticsEventDto analyticsEventDto) {
        log.info("getting action: {}, from userId: {}", analyticsEventDto.toString(), analyticsEventDto.getReceiverId());
        analyticsEventRepository.save(analyticsEventMapper.toEntity(analyticsEventDto));
        return ResponseEntity.ok().build();
    }
}
