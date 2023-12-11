import com.example.springbootexercisementoring.exceptions.UnauthorizedException;
import com.example.springbootexercisementoring.session.Session;
import com.example.springbootexercisementoring.session.SessionService;
import com.example.springbootexercisementoring.user.User;
import com.example.springbootexercisementoring.user.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private SessionService sessionService;

  public String login(String name) {
    Optional<User> userOptional = userRepository.findByName(name);
    if (userOptional.isPresent()) {
      String token = generateRandomToken();
      Session session = new Session();
      session.setToken(token);
      session.setTimestamp(LocalDateTime.now());
      session.setUser(userOptional.get());
      sessionService.createSession(userOptional.get(),session);
      return token;
    } else {
      throw new UnauthorizedException("Użytkownik nie istnieje.");
    }
  }

  public void logout(String token) {
    sessionService.removeSession(token);
  }

  private String generateRandomToken() {
    return UUID.randomUUID().toString();
  }
}
