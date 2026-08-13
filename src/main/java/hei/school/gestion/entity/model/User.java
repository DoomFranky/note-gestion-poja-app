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
public class User {
  private String id;
  private String firstName;
  private String lastName;
  private String email;
  private UserRole role;
  private Instant createdAt;
}
