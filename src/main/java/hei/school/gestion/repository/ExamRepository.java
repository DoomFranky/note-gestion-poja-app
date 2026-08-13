package hei.school.gestion.repository;

import hei.school.gestion.entity.domain.JExam;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<JExam, String> {

  @Override
  @EntityGraph(attributePaths = "course")
  List<JExam> findAll();

  @EntityGraph(attributePaths = "course")
  List<JExam> findByCourseIdAndAcademicYear(String courseId, Integer academicYear);
}
