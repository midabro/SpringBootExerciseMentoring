package com.example.springbootexercisementoring.session;

import java.time.LocalDateTime;
import java.util.UUID;


public class Session {

  private String token;

  private LocalDateTime timestamp;

  private String userId;
  private static final long sessionTimeoutMinutes = 5;
  public Session(String userId) {
    this.userId = userId;
    this.timestamp=LocalDateTime.now();
    this.token=generateRandomToken();
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

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  private String generateRandomToken() {
    return UUID.randomUUID().toString();
  }

  public boolean isSessionExpired() {
      return timestamp.plusMinutes(sessionTimeoutMinutes).isBefore(LocalDateTime.now());
  }

}
