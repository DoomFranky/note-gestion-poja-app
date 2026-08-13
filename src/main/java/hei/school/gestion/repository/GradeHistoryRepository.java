package hei.school.gestion.repository;

import hei.school.gestion.entity.domain.JGradeHistory;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeHistoryRepository extends JpaRepository<JGradeHistory, String> {

  @EntityGraph(attributePaths = {"grade", "modifiedBy"})
  List<JGradeHistory> findByGradeId(String gradeId);
}
