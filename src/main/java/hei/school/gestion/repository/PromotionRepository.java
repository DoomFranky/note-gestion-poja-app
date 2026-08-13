package hei.school.gestion.repository;

import hei.school.gestion.entity.domain.JPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<JPromotion, String> {}
