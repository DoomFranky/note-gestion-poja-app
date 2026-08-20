package hei.school.gestion.dto;

import hei.school.gestion.entity.model.UserRole;
import lombok.Data;

public class AuthDtos {

  @Data
  public static class LoginRequest {
    private String email;
    private String password;
  }

  @Data
  public static class LoginResponse {
    private String token;

    public LoginResponse(String token) {
      this.token = token;
    }
  }

  @Data
  public static class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
  }
}
