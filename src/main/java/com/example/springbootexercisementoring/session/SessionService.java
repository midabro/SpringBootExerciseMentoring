package com.example.springbootexercisementoring.session;

import com.example.springbootexercisementoring.user.User;
import org.springframework.scheduling.annotation.Scheduled;

public interface SessionService {

  void createSession(User user, Session session);

  Session getSession(String userId);

  void removeSession(String token);

  boolean isSessionValid(String token);

  boolean isSessionExpired(String token);

  @Scheduled(fixedRate = 30000)
  void removeExpiredSessions();
}
