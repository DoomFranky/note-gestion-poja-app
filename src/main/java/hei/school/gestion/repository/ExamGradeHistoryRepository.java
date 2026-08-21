package hei.school.gestion.repository;

import hei.school.gestion.entity.domain.JExamGradeHistory;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamGradeHistoryRepository extends JpaRepository<JExamGradeHistory, String> {

  @EntityGraph(attributePaths = {"examGrade", "modifiedBy"})
  List<JExamGradeHistory> findByExamGradeId(String examGradeId);
}
