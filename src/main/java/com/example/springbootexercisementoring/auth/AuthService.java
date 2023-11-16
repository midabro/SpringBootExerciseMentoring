package com.example.springbootexercisementoring.auth;

import com.example.springbootexercisementoring.exceptions.UnauthorizedException;
import com.example.springbootexercisementoring.session.Session;
import com.example.springbootexercisementoring.session.SessionService;
import com.example.springbootexercisementoring.user.User;
import com.example.springbootexercisementoring.user.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private SessionService sessionService;

  public String login(String name) {
    Optional<User> userOptional = userRepository.findByLoginName(name);
    if (userOptional.isPresent()) {
      String token = generateRandomToken();
      Session session = new Session();
      session.setToken(token);
      session.setTimestamp(LocalDateTime.now());
      session.setUser(userOptional.get());
      sessionService.createSession(userOptional.get(),session);
      logger.info("Użytkownik o loginie '{}' zalogowany. Token: {}", name, token);
      return token;
    } else {
      logger.warn("Próba logowania nieistniejącego użytkownika o loginie '{}'", name);
      throw new UnauthorizedException("Użytkownik nie istnieje.");
    }
  }

  public void logout(String token) {
    sessionService.removeSession(token);
    logger.info("Wylogowano użytkownika o tokenie '{}'", token);
  }

  private String generateRandomToken() {
    return UUID.randomUUID().toString();
  }
}
