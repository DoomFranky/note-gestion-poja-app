package hei.school.gestion.repository;

import hei.school.gestion.entity.domain.JStudentGroup;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentGroupRepository extends JpaRepository<JStudentGroup, String> {

  @EntityGraph(attributePaths = {"student", "group"})
  List<JStudentGroup> findByStudentId(String studentId);
}
