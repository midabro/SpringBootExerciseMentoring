package com.springbootexercisementoring.session;

import java.time.LocalDateTime;
import java.util.UUID;


public class Session {

  private static final long SESSION_TIMEOUT_MINUTES = 5;

  private String token;

  private final LocalDateTime timestamp;

  private final String userId;

  public Session(String userId) {
    this.userId = userId;
    this.timestamp = LocalDateTime.now();
    this.token = generateRandomToken();
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public String getUserId() {
    return userId;
  }

  private String generateRandomToken() {
    return UUID.randomUUID().toString();
  }

  public boolean isSessionExpired() {
    return timestamp.plusMinutes(SESSION_TIMEOUT_MINUTES).isBefore(LocalDateTime.now());
  }

}
