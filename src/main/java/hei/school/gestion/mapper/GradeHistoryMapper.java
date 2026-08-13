package hei.school.gestion.mapper;

import hei.school.gestion.entity.domain.JGradeHistory;
import hei.school.gestion.entity.model.GradeHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GradeHistoryMapper {

  private final GradeMapper gradeMapper;
  private final UserMapper userMapper;

  public GradeHistory toDomain(JGradeHistory entity) {
    if (entity == null) return null;

    return GradeHistory.builder()
        .id(entity.getId())
        .grade(gradeMapper.toDomain(entity.getGrade()))
        .previousScore(entity.getPreviousScore())
        .newScore(entity.getNewScore())
        .reason(entity.getReason())
        .modifiedBy(userMapper.toDomain(entity.getModifiedBy()))
        .modifiedAt(entity.getModifiedAt())
        .build();
  }

  public JGradeHistory toEntity(GradeHistory domain) {
    if (domain == null) return null;

    return JGradeHistory.builder()
        .id(domain.getId())
        .grade(gradeMapper.toEntity(domain.getGrade()))
        .previousScore(domain.getPreviousScore())
        .newScore(domain.getNewScore())
        .reason(domain.getReason())
        .modifiedBy(userMapper.toEntity(domain.getModifiedBy()))
        .modifiedAt(domain.getModifiedAt())
        .build();
  }
}
