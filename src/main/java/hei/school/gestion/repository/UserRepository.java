package hei.school.gestion.repository;

import hei.school.gestion.entity.domain.JUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<JUser, String> {

  @Override
  @EntityGraph(attributePaths = "promotion")
  Optional<JUser> findById(String id);

  @EntityGraph(attributePaths = "promotion")
  List<JUser> findByPromotionId(String promotionId);
}
