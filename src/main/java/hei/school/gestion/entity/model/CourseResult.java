package hei.school.gestion.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResult {
  private Course course;
  private Integer academicYear;
  private Double average;
  private boolean complete;
}
