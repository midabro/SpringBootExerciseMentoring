package com.example.springbootexercisementoring.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/login")
  public String login(@RequestParam String name) {
    return authService.login(name);
  }

  @PostMapping("/logout")
  public void logout(@RequestHeader("Authorization") String token) {
    authService.logout(token);
  }
}
