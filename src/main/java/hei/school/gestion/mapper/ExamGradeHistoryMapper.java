package hei.school.gestion.mapper;

import hei.school.gestion.entity.domain.JExamGradeHistory;
import hei.school.gestion.entity.model.ExamGradeHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExamGradeHistoryMapper {

  private final ExamGradeMapper examGradeMapper;
  private final UserMapper userMapper;

  public ExamGradeHistory toDomain(JExamGradeHistory entity) {
    if (entity == null) return null;

    return ExamGradeHistory.builder()
        .id(entity.getId())
        .examGrade(examGradeMapper.toDomain(entity.getExamGrade()))
        .previousScore(entity.getPreviousScore())
        .newScore(entity.getNewScore())
        .reason(entity.getReason())
        .modifiedBy(userMapper.toDomain(entity.getModifiedBy()))
        .modifiedAt(entity.getModifiedAt())
        .build();
  }

  public JExamGradeHistory toEntity(ExamGradeHistory domain) {
    if (domain == null) return null;

    return JExamGradeHistory.builder()
        .id(domain.getId())
        .examGrade(examGradeMapper.toEntity(domain.getExamGrade()))
        .previousScore(domain.getPreviousScore())
        .newScore(domain.getNewScore())
        .reason(domain.getReason())
        .modifiedBy(userMapper.toEntity(domain.getModifiedBy()))
        .modifiedAt(domain.getModifiedAt())
        .build();
  }
}
