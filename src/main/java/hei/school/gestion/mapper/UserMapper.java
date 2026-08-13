package hei.school.gestion.mapper;

import hei.school.gestion.entity.domain.JUser;
import hei.school.gestion.entity.domain.JUserRole;
import hei.school.gestion.entity.model.User;
import hei.school.gestion.entity.model.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public User toDomain(JUser entity) {
    if (entity == null) return null;

    return User.builder()
        .id(entity.getId())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .email(entity.getEmail())
        .role(toDomainRole(entity.getRole()))
        .createdAt(entity.getCreatedAt())
        .build();
  }

  public JUser toEntity(User domain) {
    if (domain == null) return null;

    return JUser.builder()
        .id(domain.getId())
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .email(domain.getEmail())
        .role(toEntityRole(domain.getRole()))
        .createdAt(domain.getCreatedAt())
        .build();
  }

  private UserRole toDomainRole(JUserRole role) {
    if (role == null) return null;
    return UserRole.valueOf(role.name());
  }

  private JUserRole toEntityRole(UserRole role) {
    if (role == null) return null;
    return JUserRole.valueOf(role.name());
  }
}
