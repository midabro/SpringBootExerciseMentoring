
import com.springbootexercisementoring.session.DefaultSessionService;
import com.springbootexercisementoring.session.SessionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionServiceConfig {
  @Bean
  public SessionService sessionService() {
    return new DefaultSessionService();
  }
}
