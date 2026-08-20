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
  private String ref;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private UserRole role;
  private Track track;
  private Promotion promotion;
  private Instant createdAt;
}
