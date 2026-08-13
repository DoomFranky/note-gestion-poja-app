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
public class Exam {
  private String id;
  private Course course;
  private Integer academicYear;
  private String label;
  private Instant examDatetime;
  private Integer coefficientNum;
  private Integer coefficientDen;
}
