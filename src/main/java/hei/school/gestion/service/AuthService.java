package hei.school.gestion.service;

import hei.school.gestion.dto.AuthDtos;
import hei.school.gestion.entity.domain.JUser;
import hei.school.gestion.entity.model.User;
import hei.school.gestion.exception.BadRequestException;
import hei.school.gestion.exception.UnauthorizedException;
import hei.school.gestion.mapper.UserMapper;
import hei.school.gestion.repository.UserRepository;
import hei.school.gestion.security.JwtProvider;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;

  public AuthDtos.LoginResponse login(AuthDtos.LoginRequest request) {
    JUser jUser =
        userRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new UnauthorizedException("Incorrect email or password"));

    if (!passwordEncoder.matches(request.getPassword(), jUser.getPassword())) {
      throw new UnauthorizedException("Incorrect email or password");
    }

    User domainUser = userMapper.toDomain(jUser);
    String token = jwtProvider.generateToken(domainUser);

    return new AuthDtos.LoginResponse(token);
  }

  public User register(AuthDtos.RegisterRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new BadRequestException("A user already got that email");
    }

    User newUser =
        User.builder()
            .id(UUID.randomUUID().toString())
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole())
            .build();

    JUser savedEntity = userRepository.save(userMapper.toEntity(newUser));
    return userMapper.toDomain(savedEntity);
  }
}
