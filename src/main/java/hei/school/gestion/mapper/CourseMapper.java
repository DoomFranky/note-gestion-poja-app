package hei.school.gestion.mapper;

import hei.school.gestion.entity.domain.JCourse;
import hei.school.gestion.entity.model.Course;
import java.util.Collections;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseMapper {

  private final UserMapper userMapper;
  private final GroupMapper groupMapper;

  public Course toDomain(JCourse entity) {
    if (entity == null) return null;

    return Course.builder()
        .id(entity.getId())
        .code(entity.getCode())
        .name(entity.getName())
        .credits(entity.getCredits())
        .teachers(
            entity.getTeachers() != null
                ? entity.getTeachers().stream()
                    .map(userMapper::toDomain)
                    .collect(Collectors.toSet())
                : Collections.emptySet())
        .groups(
            entity.getGroups() != null
                ? entity.getGroups().stream().map(groupMapper::toDomain).collect(Collectors.toSet())
                : Collections.emptySet())
        .build();
  }

  public JCourse toEntity(Course domain) {
    if (domain == null) return null;

    return JCourse.builder()
        .id(domain.getId())
        .code(domain.getCode())
        .name(domain.getName())
        .credits(domain.getCredits())
        .teachers(
            domain.getTeachers() != null
                ? domain.getTeachers().stream()
                    .map(userMapper::toEntity)
                    .collect(Collectors.toSet())
                : Collections.emptySet())
        .groups(
            domain.getGroups() != null
                ? domain.getGroups().stream().map(groupMapper::toEntity).collect(Collectors.toSet())
                : Collections.emptySet())
        .build();
  }
}
