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
public class GradeHistory {
  private String id;
  private Grade grade;
  private Double previousScore;
  private Double newScore;
  private String reason;
  private User modifiedBy;
  private Instant modifiedAt;
}
