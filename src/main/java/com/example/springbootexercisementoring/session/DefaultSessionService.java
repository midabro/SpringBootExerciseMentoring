package com.example.springbootexercisementoring.session;

import com.example.springbootexercisementoring.user.User;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DefaultSessionService implements SessionService{
  private static final Logger logger = LoggerFactory.getLogger(DefaultSessionService.class);

  private final long sessionTimeoutMinutes = 5;
  private final Map<String, Session> sessionMap = new ConcurrentHashMap<>();
  private final Object sessionLock = new Object();

  public void createSession(User user, Session session) {

    synchronized (sessionLock) {
      sessionMap.put(user.getId(), session);
      logger.info("Utworzono sesję dla użytkownika o ID: {}", user.getId());
    }
  }

  public Session getSession(String userId) {
    return sessionMap.get(userId);
  }

  public void removeSession(String token) {
    synchronized (sessionLock) {
      for (Map.Entry<String, Session> entry : sessionMap.entrySet()) {
        if (entry.getValue().getToken().equals(token)) {
         String userId = entry.getKey();
          sessionMap.remove(userId);
          logger.info("Usunięto sesję dla użytkownika o ID: {}", userId);
          break;
        }
      }
    }
  }

  public boolean isSessionValid(String token) {
    for (Session sessionInfo : sessionMap.values()) {
      if (sessionInfo.getToken().equals(token) && isSessionExpired(sessionInfo.getToken())) {
        return true;
      }
    }
    return false;
  }

  public boolean isSessionExpired(String token) {
    Session sessionInfo = sessionMap.get(token);

    if (sessionInfo == null) {
      return false;
    }

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime sessionTimestamp = sessionInfo.getTimestamp();
    long elapsedTimeMinutes = java.time.Duration.between(sessionTimestamp, now).toMinutes();

    return elapsedTimeMinutes <= sessionTimeoutMinutes;
  }

  @Scheduled(fixedRate = 30000)
  public void removeExpiredSessions() {
    synchronized (sessionLock) {
      LocalDateTime now = LocalDateTime.now();

      sessionMap.entrySet().removeIf(entry -> {
        Session session = entry.getValue();
        long elapsedTimeMinutes = java.time.Duration.between(session.getTimestamp(), now).toMinutes();
        boolean isExpired = elapsedTimeMinutes > sessionTimeoutMinutes;
        if (isExpired) {
          logger.info("Usunięto wygasłą sesję dla użytkownika o ID: {}", session.getUser().getId());
        }
        return isExpired;
      });
      logger.info("Liczba usuniętych wygasłych sesji: {}", sessionMap.size());
    }
  }
}
