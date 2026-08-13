package hei.school.gestion.mapper;

import hei.school.gestion.entity.domain.JExamGrade;
import hei.school.gestion.entity.model.ExamGrade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExamGradeMapper {

  private final ExamMapper examMapper;
  private final UserMapper userMapper;

  public ExamGrade toDomain(JExamGrade entity) {
    if (entity == null) return null;

    return ExamGrade.builder()
        .id(entity.getId())
        .exam(examMapper.toDomain(entity.getExam()))
        .student(userMapper.toDomain(entity.getStudent()))
        .score(entity.getScore())
        .updatedAt(entity.getUpdatedAt())
        .updatedBy(userMapper.toDomain(entity.getUpdatedBy()))
        .build();
  }

  public JExamGrade toEntity(ExamGrade domain) {
    if (domain == null) return null;

    return JExamGrade.builder()
        .id(domain.getId())
        .exam(examMapper.toEntity(domain.getExam()))
        .student(userMapper.toEntity(domain.getStudent()))
        .score(domain.getScore())
        .updatedAt(domain.getUpdatedAt())
        .updatedBy(userMapper.toEntity(domain.getUpdatedBy()))
        .build();
  }
}
