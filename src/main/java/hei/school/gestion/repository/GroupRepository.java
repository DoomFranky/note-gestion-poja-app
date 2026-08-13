package hei.school.gestion.repository;

import hei.school.gestion.entity.domain.JGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<JGroup, String> {}
