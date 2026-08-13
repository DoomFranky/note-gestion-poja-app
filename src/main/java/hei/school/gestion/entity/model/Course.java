package hei.school.gestion.entity.model;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
  private String id;
  private String code;
  private String name;
  private Integer credits;

  @Builder.Default private Set<User> teachers = new HashSet<>();

  @Builder.Default private Set<Group> groups = new HashSet<>();
}
