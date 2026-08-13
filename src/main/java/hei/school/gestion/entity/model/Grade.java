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
public class Grade {
  private String id;
  private User student;
  private Course course;
  private Double score;
  private Instant updatedAt;
  private User updatedBy;
}
