package com.example.springbootexercisementoring.session;

import com.example.springbootexercisementoring.user.User;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

public class RWLSessionService implements SessionService {

  private static final Logger logger = LoggerFactory.getLogger(RWLSessionService.class);
  private final long sessionTimeoutMinutes = 5;
  private final Map<String, Session> sessionMap = new ConcurrentHashMap<>();
  private final ReadWriteLock sessionLock = new ReentrantReadWriteLock();

  public void createSession(User user, Session session) {
    sessionLock.writeLock().lock();
    try {
      sessionMap.put(user.getId(), session);
      logger.info("Utworzono sesję dla użytkownika o ID: {}", user.getId());
    } finally {
      sessionLock.writeLock().unlock();
    }
  }

  public Session getSession(String userId) {
    sessionLock.readLock().lock();
    try {
      return sessionMap.get(userId);
    } finally {
      sessionLock.readLock().unlock();
    }
  }

  public void removeSession(String token) {
    sessionLock.writeLock().lock();
    try {
      for (Map.Entry<String, Session> entry : sessionMap.entrySet()) {
        if (entry.getValue().getToken().equals(token)) {
          String userId = entry.getKey();
          sessionMap.remove(userId);
          logger.info("Usunięto sesję dla użytkownika o ID: {}", userId);
          break;
        }
      }
    } finally {
      sessionLock.writeLock().unlock();
    }
  }

  public boolean isSessionValid(String token) {
    sessionLock.readLock().lock();
    try {
      for (Session sessionInfo : sessionMap.values()) {
        if (sessionInfo.getToken().equals(token)) {
          return true;
        }
      }
      return false;
    } finally {
      sessionLock.readLock().unlock();
    }
  }

  public boolean isSessionExpired(String token) {
    sessionLock.readLock().lock();
    try {
      Session sessionInfo = sessionMap.get(token);

      if (sessionInfo == null) {
        return false;
      }

      LocalDateTime now = LocalDateTime.now();
      LocalDateTime sessionTimestamp = sessionInfo.getTimestamp();
      long elapsedTimeMinutes = java.time.Duration.between(sessionTimestamp, now).toMinutes();

      return elapsedTimeMinutes <= sessionTimeoutMinutes;
    } finally {
      sessionLock.readLock().unlock();
    }
  }

  @Scheduled(fixedRate = 30000)
  public void removeExpiredSessions() {
    sessionLock.writeLock().lock();
    try {
      LocalDateTime now = LocalDateTime.now();

      sessionMap.entrySet().removeIf(entry -> {
        Session session = entry.getValue();
        long elapsedTimeMinutes = java.time.Duration.between(session.getTimestamp(), now)
            .toMinutes();
        boolean isExpired = elapsedTimeMinutes > sessionTimeoutMinutes;
        if (isExpired) {
          logger.info("Usunięto wygasłą sesję dla użytkownika o ID: {}", session.getUser().getId());
        }
        return isExpired;
      });
      logger.info("Liczba pozostających sesji: {}", sessionMap.size());
    } finally {
      sessionLock.writeLock().unlock();
    }
  }

}
