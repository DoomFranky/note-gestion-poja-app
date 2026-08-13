package hei.school.gestion.entity.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bulletin {
  private User student;
  private List<YearResult> years;
  private Double overallAverage;
  private Integer totalCredits;
}
