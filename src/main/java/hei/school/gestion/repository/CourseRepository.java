package hei.school.gestion.repository;

import hei.school.gestion.entity.domain.JCourse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<JCourse, String> {

  @Override
  @EntityGraph(attributePaths = {"teachers", "groups"})
  Optional<JCourse> findById(String id);

  @EntityGraph(attributePaths = {"teachers", "groups"})
  List<JCourse> findByGroupsIdIn(List<String> groupIds);
}
