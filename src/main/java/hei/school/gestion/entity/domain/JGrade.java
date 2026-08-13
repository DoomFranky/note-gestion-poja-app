package hei.school.gestion.entity.domain;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(
    name = "grade",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "unique_student_course",
          columnNames = {"student_id", "course_id"})
    })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JGrade {

  @Id
  @Column(length = 50)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false)
  private JUser student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id", nullable = false)
  private JCourse course;

  @Column(nullable = false)
  private Double score;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Instant updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "updated_by", nullable = false)
  private JUser updatedBy;
}
