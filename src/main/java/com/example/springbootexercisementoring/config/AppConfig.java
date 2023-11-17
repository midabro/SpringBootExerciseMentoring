package com.example.springbootexercisementoring.config;

import com.example.springbootexercisementoring.session.DefaultSessionService;
import com.example.springbootexercisementoring.session.SessionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  @Bean
  public SessionService sessionService() {
    return new DefaultSessionService();
//    return new RWLSessionService();
  }
}
