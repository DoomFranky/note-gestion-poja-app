package hei.school.gestion.entity.domain;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "grade_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JGradeHistory {

  @Id
  @Column(length = 50)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "grade_id", nullable = false)
  private JGrade grade;

  @Column(name = "previous_score", nullable = false)
  private Double previousScore;

  @Column(name = "new_score", nullable = false)
  private Double newScore;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String reason;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modified_by", nullable = false)
  private JUser modifiedBy;

  @CreationTimestamp
  @Column(name = "modified_at", updatable = false)
  private Instant modifiedAt;
}
