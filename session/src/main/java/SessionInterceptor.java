import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SessionInterceptor implements HandlerInterceptor {

  @Autowired
  private SessionService sessionService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    String requestURI = request.getRequestURI();
    // Sprawdź, czy to endpoint /login lub /logout
    if (isLoginOrLogoutEndpoint(requestURI)) {
      return true;
    }

    String sessionToken = request.getHeader("Authorization");

    // Sprawdź, czy sesja istnieje
    if (sessionToken != null && sessionService.isSessionValid(sessionToken)) {
      if (sessionService.isSessionExpired(sessionToken)) {
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
