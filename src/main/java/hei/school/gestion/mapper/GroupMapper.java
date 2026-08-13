package hei.school.gestion.mapper;

import hei.school.gestion.entity.domain.JGroup;
import hei.school.gestion.entity.model.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {

  public Group toDomain(JGroup entity) {
    if (entity == null) return null;

    return Group.builder()
        .id(entity.getId())
        .name(entity.getName())
        .academicYear(entity.getAcademicYear())
        .createdAt(entity.getCreatedAt())
        .build();
  }

  public JGroup toEntity(Group domain) {
    if (domain == null) return null;

    return JGroup.builder()
        .id(domain.getId())
        .name(domain.getName())
        .academicYear(domain.getAcademicYear())
        .createdAt(domain.getCreatedAt())
        .build();
  }
}
