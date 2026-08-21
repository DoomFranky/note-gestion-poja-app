package hei.school.gestion.entity.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamGradeHistory {
  private String id;
  private ExamGrade examGrade;
  private Double previousScore;
  private Double newScore;
  private String reason;
  private User modifiedBy;
  private Instant modifiedAt;
}
