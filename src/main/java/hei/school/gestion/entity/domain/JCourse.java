package hei.school.gestion.entity.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JCourse {

  @Id
  @Column(length = 50)
  private String id;

  @Column(nullable = false, unique = true, length = 20)
  private String code;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false)
  private Integer credits;

  @ManyToMany
  @JoinTable(
      name = "course_teacher",
      joinColumns = @JoinColumn(name = "course_id"),
      inverseJoinColumns = @JoinColumn(name = "teacher_id"))
  @Builder.Default
  private Set<JUser> teachers = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "course_group",
      joinColumns = @JoinColumn(name = "course_id"),
      inverseJoinColumns = @JoinColumn(name = "group_id"))
  @Builder.Default
  private Set<JGroup> groups = new HashSet<>();
}
