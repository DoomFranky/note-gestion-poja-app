package hei.school.gestion.mapper;

import hei.school.gestion.entity.domain.JGrade;
import hei.school.gestion.entity.model.Grade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GradeMapper {

  private final UserMapper userMapper;
  private final CourseMapper courseMapper;

  public Grade toDomain(JGrade entity) {
    if (entity == null) return null;

    return Grade.builder()
        .id(entity.getId())
        .student(userMapper.toDomain(entity.getStudent()))
        .course(courseMapper.toDomain(entity.getCourse()))
        .score(entity.getScore())
        .updatedAt(entity.getUpdatedAt())
        .updatedBy(userMapper.toDomain(entity.getUpdatedBy()))
        .build();
  }

  public JGrade toEntity(Grade domain) {
    if (domain == null) return null;

    return JGrade.builder()
        .id(domain.getId())
        .student(userMapper.toEntity(domain.getStudent()))
        .course(courseMapper.toEntity(domain.getCourse()))
        .score(domain.getScore())
        .updatedAt(domain.getUpdatedAt())
        .updatedBy(userMapper.toEntity(domain.getUpdatedBy()))
        .build();
  }
}
