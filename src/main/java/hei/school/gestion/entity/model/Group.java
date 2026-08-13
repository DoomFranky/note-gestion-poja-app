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
public class Group {
  private String id;
  private String name;
  private String academicYear;
  private Instant createdAt;
}
