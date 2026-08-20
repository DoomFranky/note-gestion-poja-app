package hei.school.gestion.endpoint.rest.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hei.school.gestion.conf.FacadeIT;
import hei.school.gestion.dto.AuthDtos;
import hei.school.gestion.entity.domain.JUser;
import hei.school.gestion.entity.domain.JUserRole;
import hei.school.gestion.entity.model.UserRole;
import hei.school.gestion.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest extends FacadeIT {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    JUser admin =
        JUser.builder()
            .id(UUID.randomUUID().toString())
            .firstName("Admin")
            .lastName("Sys")
            .email("admin@test.com")
            .password(passwordEncoder.encode("admin123"))
            .role(JUserRole.ADMIN)
            .build();

    userRepository.save(admin);
  }

  @Test
  void register_WithoutAuth_ShouldReturnForbidden() throws Exception {
    AuthDtos.RegisterRequest request = new AuthDtos.RegisterRequest();
    request.setFirstName("John");
    request.setLastName("Doe");
    request.setEmail("john.doe@test.com");
    request.setPassword("password123");
    request.setRole(UserRole.STUDENT);

    mockMvc
        .perform(
            post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void register_AsAdmin_ShouldSuccess() throws Exception {
    AuthDtos.RegisterRequest request = new AuthDtos.RegisterRequest();
    request.setFirstName("John");
    request.setLastName("Doe");
    request.setEmail("john.doe@test.com");
    request.setPassword("password123");
    request.setRole(UserRole.STUDENT);

    mockMvc
        .perform(
            post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.email").value("john.doe@test.com"))
        .andExpect(jsonPath("$.firstName").value("John"));
  }

  @Test
  void login_WithValidCredentials_ShouldReturnToken() throws Exception {
    AuthDtos.LoginRequest loginRequest = new AuthDtos.LoginRequest();
    loginRequest.setEmail("admin@test.com");
    loginRequest.setPassword("admin123");

    mockMvc
        .perform(
            post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").exists());
  }

  @Test
  void login_WithInvalidCredentials_ShouldFail() throws Exception {
    AuthDtos.LoginRequest loginRequest = new AuthDtos.LoginRequest();
    loginRequest.setEmail("admin@test.com");
    loginRequest.setPassword("wrongpassword");

    mockMvc
        .perform(
            post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isUnauthorized());
  }
}
