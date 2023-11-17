package com.example.springbootexercisementoring.session;

import com.example.springbootexercisementoring.user.User;
import java.time.LocalDateTime;


public class Session {


  private String token;

  private LocalDateTime timestamp;

  private User user;

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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
