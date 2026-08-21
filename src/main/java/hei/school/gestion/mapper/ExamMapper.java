package hei.school.gestion.mapper;

import hei.school.gestion.entity.domain.JExam;
import hei.school.gestion.entity.domain.JExamType;
import hei.school.gestion.entity.model.Exam;
import hei.school.gestion.entity.model.ExamType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExamMapper {

  private final CourseMapper courseMapper;

  public Exam toDomain(JExam entity) {
    if (entity == null) return null;

    return Exam.builder()
        .id(entity.getId())
        .course(courseMapper.toDomain(entity.getCourse()))
        .academicYear(entity.getAcademicYear())
        .label(entity.getLabel())
        .type(toDomainType(entity.getType()))
        .examDatetime(entity.getExamDatetime())
        .coefficientNum(entity.getCoefficientNum())
        .coefficientDen(entity.getCoefficientDen())
        .build();
  }

  public JExam toEntity(Exam domain) {
    if (domain == null) return null;

    return JExam.builder()
        .id(domain.getId())
        .course(courseMapper.toEntity(domain.getCourse()))
        .academicYear(domain.getAcademicYear())
        .label(domain.getLabel())
        .type(toEntityType(domain.getType()))
        .examDatetime(domain.getExamDatetime())
        .coefficientNum(domain.getCoefficientNum())
        .coefficientDen(domain.getCoefficientDen())
        .build();
  }

  private ExamType toDomainType(JExamType type) {
    if (type == null) return null;
    return ExamType.valueOf(type.name());
  }

  private JExamType toEntityType(ExamType type) {
    if (type == null) return null;
    return JExamType.valueOf(type.name());
  }
}
