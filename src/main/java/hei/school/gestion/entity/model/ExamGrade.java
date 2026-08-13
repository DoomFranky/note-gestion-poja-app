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
public class ExamGrade {
  private String id;
  private Exam exam;
  private User student;
  private Double score;
  private Instant updatedAt;
  private User updatedBy;
}
