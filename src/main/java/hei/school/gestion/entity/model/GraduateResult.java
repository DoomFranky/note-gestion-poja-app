package hei.school.gestion.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GraduateResult {
  private User student;
  private Double overallAverage;
  private Integer rank;
}
