package com.example.springbootexercisementoring.session;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class SessionInterceptor implements HandlerInterceptor {

  @Autowired
  private SessionService sessionService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    String requestURI = request.getRequestURI();
    // Check if it is endpoint /login or /logout
    if (isLoginOrLogoutEndpoint(requestURI)) {
      return true;
    }

    String sessionToken = request.getHeader("Authorization");
    Optional<Session> sessionOptional = sessionService.getSession(sessionToken);
    // Check whether a session exists
    if (sessionOptional.isPresent()) {
      if (sessionOptional.get().isSessionExpired()) {
        return true;
      } else {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session has expired");
        return false;
      }
    } else {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid session token");
      return false;
    }
  }

  private boolean isLoginOrLogoutEndpoint(String requestURI) {
    return "/login".equals(requestURI) || "/logout".equals(requestURI);
  }
}
