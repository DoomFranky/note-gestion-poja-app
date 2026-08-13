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
public class StudentGroup {
  private String id;
  private User student;
  private Group group;
  private Instant joinedAt;
  private Instant leftAt;
}
