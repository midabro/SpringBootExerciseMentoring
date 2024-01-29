package com.springbootexercisementoring.session;

import com.springbootexercisementoring.user.User;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface SessionService {

  void createSession(User user);

  void removeSession(Session session);

  Optional<Session> getSession(String token);

  void removeExpiredSessions();

  void login(String name);

  void logout(String token);
}
