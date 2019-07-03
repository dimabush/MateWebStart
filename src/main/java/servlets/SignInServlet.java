package servlets;

import accounts.AccountService;
import templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignInServlet extends HttpServlet {
  private AccountService accountService;

  public SignInServlet(AccountService accountService) {
    this.accountService = accountService;
  }

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response) throws IOException {
    Map<String, Object> pageVariables = new HashMap<>();
    pageVariables.put("login", "");
    pageVariables.put("password", "");
    pageVariables.put("sessionId", request.getSession().getId());


    response.getWriter().println(PageGenerator.instance().getPage("signin.html", pageVariables));
    response.setContentType("text/html;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);
  }

  public void doPost(HttpServletRequest request,
                     HttpServletResponse response) throws IOException {
    String login = request.getParameter("login");
    String password = request.getParameter("password");
    String session = request.getSession().getId();

    if (accountService.getUserByLogin(login).getPassword().equals(password) &&
        !accountService.userIsLoggedIn(session)) {
      accountService.loginUser(session, accountService.getUserByLogin(login));
      response.setStatus(HttpServletResponse.SC_OK);
      response.getWriter().println("Authorized: " + login);
    } else {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().println("Unauthorized");
    }
  }
}
