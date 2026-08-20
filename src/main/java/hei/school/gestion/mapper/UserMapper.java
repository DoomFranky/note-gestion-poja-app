package hei.school.gestion.mapper;

import hei.school.gestion.entity.domain.JTrack;
import hei.school.gestion.entity.domain.JUser;
import hei.school.gestion.entity.domain.JUserRole;
import hei.school.gestion.entity.model.Track;
import hei.school.gestion.entity.model.User;
import hei.school.gestion.entity.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private final PromotionMapper promotionMapper;

  public User toDomain(JUser entity) {
    if (entity == null) return null;

    return User.builder()
        .id(entity.getId())
        .ref(entity.getRef())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .email(entity.getEmail())
        .password(entity.getPassword())
        .role(toDomainRole(entity.getRole()))
        .track(toDomainTrack(entity.getTrack()))
        .promotion(promotionMapper.toDomain(entity.getPromotion()))
        .createdAt(entity.getCreatedAt())
        .build();
  }

  public JUser toEntity(User domain) {
    if (domain == null) return null;

    return JUser.builder()
        .id(domain.getId())
        .ref(domain.getRef())
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .email(domain.getEmail())
        .password(domain.getPassword())
        .role(toEntityRole(domain.getRole()))
        .track(toEntityTrack(domain.getTrack()))
        .promotion(promotionMapper.toEntity(domain.getPromotion()))
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

  private Track toDomainTrack(JTrack track) {
    if (track == null) return null;
    return Track.valueOf(track.name());
  }

  private JTrack toEntityTrack(Track track) {
    if (track == null) return null;
    return JTrack.valueOf(track.name());
  }
}
