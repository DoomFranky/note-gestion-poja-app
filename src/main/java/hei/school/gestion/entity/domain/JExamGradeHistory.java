package hei.school.gestion.entity.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "exam_grade_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JExamGradeHistory {

  @Id
  @Column(length = 50)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "exam_grade_id", nullable = false)
  private JExamGrade examGrade;

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
