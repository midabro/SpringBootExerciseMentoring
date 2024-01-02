package com.springbootexercisementoring.auth;

import com.springbootexercisementoring.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

  private final SessionService sessionService;

  public AuthController(SessionService sessionService) {
    this.sessionService = sessionService;
  }

  @PostMapping("/login")
  public void login(@RequestParam String name) {
     sessionService.login(name);
  }

  @PostMapping("/logout")
  public void logout(@RequestHeader("Authorization") String token) {
    sessionService.logout(token);
  }
}
