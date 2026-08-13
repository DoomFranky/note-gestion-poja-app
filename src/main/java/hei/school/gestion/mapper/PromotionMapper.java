package hei.school.gestion.mapper;

import hei.school.gestion.entity.domain.JPromotion;
import hei.school.gestion.entity.model.Promotion;
import org.springframework.stereotype.Component;

@Component
public class PromotionMapper {

  public Promotion toDomain(JPromotion entity) {
    if (entity == null) return null;

    return Promotion.builder()
        .id(entity.getId())
        .label(entity.getLabel())
        .createdAt(entity.getCreatedAt())
        .build();
  }

  public JPromotion toEntity(Promotion domain) {
    if (domain == null) return null;

    return JPromotion.builder()
        .id(domain.getId())
        .label(domain.getLabel())
        .createdAt(domain.getCreatedAt())
        .build();
  }
}
