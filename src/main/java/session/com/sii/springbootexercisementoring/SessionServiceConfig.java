package session.com.sii.springbootexercisementoring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionServiceConfig {
  @Bean
  public SessionService sessionService() {
    return new DefaultSessionService();
  }
}
