package com.example.springbootexercisementoring.session;

import com.example.springbootexercisementoring.exceptions.UnauthorizedException;
import com.example.springbootexercisementoring.user.User;
import com.example.springbootexercisementoring.user.UserRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DefaultSessionService implements SessionService {

  @Autowired
  private UserRepository userRepository;
  private static final Logger logger = LoggerFactory.getLogger(DefaultSessionService.class);

  private final long sessionTimeoutMinutes = 5;
  private final Map<String, Session> sessionMap = new HashMap<>();
  private final Object sessionLock = new Object();

  public void createSession(User user) {
    Session session = new Session(user.getId());
    synchronized (sessionLock) {
      sessionMap.put(session.getToken(), session);
      logger.info("A session has been created for a user with ID: {}", user.getId());
    }
  }

  public Session getSession(String token) {
    return sessionMap.get(token);
  }

  public void removeSession(Session session) {
    synchronized (sessionLock) {
      sessionMap.remove(session.getToken());
      logger.info("A session for a user with ID has been deleted: {}", session.getUserId());
    }
  }

  public Optional<Session> isSessionValid(String token) {
    if (sessionMap.containsKey(token)) {
      Session session = sessionMap.get(token);
      return Optional.of(session);
    }
    return Optional.empty();
  }

  @Scheduled(fixedRate = 30000)
  public void removeExpiredSessions() {
    synchronized (sessionLock) {
      LocalDateTime now = LocalDateTime.now();

      sessionMap.entrySet().removeIf(entry -> {
        Session session = entry.getValue();
        long elapsedTimeMinutes = java.time.Duration.between(session.getTimestamp(), now)
            .toMinutes();
        boolean isExpired = elapsedTimeMinutes > sessionTimeoutMinutes;
        if (isExpired) {
          logger.info("Deleted an expired session for a user with ID: {}", session.getUserId());
        }
        return isExpired;
      });
      logger.info("Number of remaining sessions: {}", sessionMap.size());
    }
  }

  @Override
  public void login(String name) {
    Optional<User> userOptional = userRepository.findByLoginName(name);
    if (userOptional.isPresent()) {
      createSession(userOptional.get());
      logger.info("User with login '{}' logged in", name);
    } else {
      logger.warn("Attempting to log in a non-existent user with login '{}'", name);
      throw new UnauthorizedException("The user does not exist.");
    }
  }

  @Override
  public void logout(String token) {
    Optional<Session> sessionOptional = isSessionValid(token);
    if (sessionOptional.isPresent()) {
      removeSession(sessionOptional.get());
      logger.info("Logged out user with userId '{}'", sessionOptional.get().getUserId());
    } else {
      logger.info("Session with token '{}' does not exist", token);
    }

  }
}
