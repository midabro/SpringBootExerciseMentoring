package com.example.springbootexercisementoring.session;

import com.example.springbootexercisementoring.user.User;

public interface SessionService {

  void createSession(User user, Session session);

  Session getSession(String userId);

  void removeSession(String token);

  boolean isSessionValid(String token);

  boolean isSessionExpired(String token);

  void removeExpiredSessions();
}
