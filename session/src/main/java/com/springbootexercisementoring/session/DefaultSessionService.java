package com.springbootexercisementoring.session;

import com.springbootexercisementoring.exceptions.UnauthorizedException;
import com.springbootexercisementoring.user.User;
import com.springbootexercisementoring.user.UserRepository;
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

  private static final Logger logger = LoggerFactory.getLogger(DefaultSessionService.class);

  private static final long SESSION_TIMEOUT_MINUTES = 5;
  private final Map<String, Session> sessionMap = new HashMap<>();
  @Autowired
  private UserRepository userRepository;

  public void createSession(User user) {
    Session session = new Session(user.getId());
    synchronized (sessionMap) {
      sessionMap.put(session.getToken(), session);
      logger.info("A session has been created for a user with ID: {}", user.getId());
    }
  }

  public void removeSession(Session session) {
    synchronized (sessionMap) {
      sessionMap.remove(session.getToken());
      logger.info("A session for a user with ID has been deleted: {}", session.getUserId());
    }
  }

  public Optional<Session> getSession(String token) {
    return Optional.ofNullable(token).map(sessionMap::get);
  }

  @Scheduled(fixedRate = 30000)
  public void removeExpiredSessions() {
    synchronized (sessionMap) {
      LocalDateTime now = LocalDateTime.now();

      sessionMap.entrySet().removeIf(entry -> {
        Session session = entry.getValue();
        long elapsedTimeMinutes = java.time.Duration.between(session.getTimestamp(), now)
            .toMinutes();
        boolean isExpired = elapsedTimeMinutes > SESSION_TIMEOUT_MINUTES;
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
    Optional<Session> sessionOptional = getSession(token);
    if (sessionOptional.isPresent()) {
      removeSession(sessionOptional.get());
      logger.info("Logged out user with userId '{}'", sessionOptional.get().getUserId());
    } else {
      logger.info("Session with token '{}' does not exist", token);
    }

  }
}
