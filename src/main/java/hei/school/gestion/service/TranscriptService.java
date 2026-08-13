package hei.school.gestion.service;

import hei.school.gestion.endpoint.rest.dto.TranscriptResponse;
import hei.school.gestion.entity.model.Bulletin;
import hei.school.gestion.mapper.TranscriptDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranscriptService {

  private final GradeCalculationService gradeCalculationService;
  private final TranscriptDtoMapper transcriptDtoMapper;

  public TranscriptResponse getTranscript(String studentId) {
    Bulletin bulletin = gradeCalculationService.computeBulletin(studentId);
    return transcriptDtoMapper.toResponse(bulletin);
  }
}
