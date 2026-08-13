package hei.school.gestion.entity.domain;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "student_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JStudentGroup {

  @Id
  @Column(length = 50)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false)
  private JUser student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "group_id", nullable = false)
  private JGroup group;

  @Column(name = "joined_at")
  private Instant joinedAt;

  @Column(name = "left_at")
  private Instant leftAt;
}
