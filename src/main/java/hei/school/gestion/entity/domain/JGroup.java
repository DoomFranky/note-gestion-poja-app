package hei.school.gestion.entity.domain;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "\"group\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JGroup {

  @Id
  @Column(length = 50)
  private String id;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(name = "academic_year", nullable = false, length = 20)
  private String academicYear;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private Instant createdAt;
}
