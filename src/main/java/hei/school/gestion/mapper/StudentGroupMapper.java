package hei.school.gestion.mapper;

import hei.school.gestion.entity.domain.JStudentGroup;
import hei.school.gestion.entity.model.StudentGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentGroupMapper {

  private final UserMapper userMapper;
  private final GroupMapper groupMapper;

  public StudentGroup toDomain(JStudentGroup entity) {
    if (entity == null) return null;

    return StudentGroup.builder()
        .id(entity.getId())
        .student(userMapper.toDomain(entity.getStudent()))
        .group(groupMapper.toDomain(entity.getGroup()))
        .joinedAt(entity.getJoinedAt())
        .leftAt(entity.getLeftAt())
        .build();
  }

  public JStudentGroup toEntity(StudentGroup domain) {
    if (domain == null) return null;

    return JStudentGroup.builder()
        .id(domain.getId())
        .student(userMapper.toEntity(domain.getStudent()))
        .group(groupMapper.toEntity(domain.getGroup()))
        .joinedAt(domain.getJoinedAt())
        .leftAt(domain.getLeftAt())
        .build();
  }
}
