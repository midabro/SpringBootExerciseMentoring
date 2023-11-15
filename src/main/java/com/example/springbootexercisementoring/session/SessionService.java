package com.example.springbootexercisementoring.session;

import com.example.springbootexercisementoring.user.User;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

  private final long sessionTimeoutMinutes = 5;
  private final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

  public void createSession(User user, Session session) {
    sessionMap.put(user.getId(), session);
  }

  public Session getSession(String userId) {
    return sessionMap.get(userId);
  }

  public void removeSession(String token) {
    for (Map.Entry<String, Session> entry : sessionMap.entrySet()) {
      if (entry.getValue().getToken().equals(token)) {
       String userId = entry.getKey();
        sessionMap.remove(userId);
        break; 
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
    LocalDateTime now = LocalDateTime.now();

    sessionMap.entrySet().removeIf(entry -> {
      Session session = entry.getValue();
      long elapsedTimeMinutes = java.time.Duration.between(session.getTimestamp(), now).toMinutes();
      return elapsedTimeMinutes > sessionTimeoutMinutes;
    });
  }
}
