package com.example.springbootexercisementoring.session;

import com.example.springbootexercisementoring.exceptions.UnauthorizedException;
import com.example.springbootexercisementoring.user.User;
import com.example.springbootexercisementoring.user.UserRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


public class RWLSessionService implements SessionService {

  private static final Logger logger = LoggerFactory.getLogger(RWLSessionService.class);
  private final long sessionTimeoutMinutes = 5;
  private final Map<String, Session> sessionMap = new HashMap<>();
  private final ReadWriteLock sessionLock = new ReentrantReadWriteLock();
  @Autowired
  private UserRepository userRepository;

  public void createSession(User user) {
    sessionLock.writeLock().lock();
    try {
      Session session = new Session(user.getId());
      sessionMap.put(user.getId(),session);
      logger.info("A session has been created for a user with ID: {}", user.getId());
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

  public void removeSession(Session session) {
    sessionLock.writeLock().lock();
    try {
          sessionMap.remove(session.getUserId());
          logger.info("A session for a user with ID has been deleted: {}", session.getUserId());
    } finally {
      sessionLock.writeLock().unlock();
    }
  }

  public Optional<Session> isSessionValid(String token) {
    sessionLock.readLock().lock();
    try {
      for (Session sessionInfo : sessionMap.values()) {
        if (sessionInfo.getToken().equals(token)) {
          return Optional.of(sessionInfo);
        }
      }
      return Optional.empty();
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
          logger.info("Deleted an expired session for a user with ID: {}", session.getUserId());
        }
        return isExpired;
      });
      logger.info("Number of remaining sessions: {}", sessionMap.size());
    } finally {
      sessionLock.writeLock().unlock();
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

  }

}
