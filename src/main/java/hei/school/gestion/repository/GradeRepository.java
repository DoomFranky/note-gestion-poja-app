package hei.school.gestion.repository;

import hei.school.gestion.entity.domain.JGrade;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<JGrade, String> {

  @Override
  @EntityGraph(attributePaths = {"student", "course", "updatedBy"})
  List<JGrade> findAll();

  @EntityGraph(attributePaths = {"student", "course", "updatedBy"})
  Optional<JGrade> findByStudentIdAndCourseIdAndAcademicYear(
      String studentId, String courseId, Integer academicYear);
}
