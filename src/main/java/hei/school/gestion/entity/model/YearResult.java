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
public class YearResult {
  private Integer year;
  private boolean provisional;
  private Double average;
  private Integer credits;
  private List<CourseResult> courses;
}
