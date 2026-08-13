package hei.school.gestion.repository;

import hei.school.gestion.entity.domain.JExamGrade;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamGradeRepository extends JpaRepository<JExamGrade, String> {

  @EntityGraph(attributePaths = {"exam.course", "student"})
  List<JExamGrade> findByStudentId(String studentId);

  @EntityGraph(attributePaths = {"exam.course", "student"})
  List<JExamGrade> findByStudentIdAndExamCourseId(String studentId, String courseId);
}
