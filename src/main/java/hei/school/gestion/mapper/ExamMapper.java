package hei.school.gestion.mapper;

import hei.school.gestion.entity.domain.JExam;
import hei.school.gestion.entity.model.Exam;
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
        .examDatetime(domain.getExamDatetime())
        .coefficientNum(domain.getCoefficientNum())
        .coefficientDen(domain.getCoefficientDen())
        .build();
  }
}
