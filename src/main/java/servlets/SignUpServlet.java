package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUpServlet extends HttpServlet {

  private AccountService accountService;

  public SignUpServlet(AccountService accountService) {
    this.accountService = accountService;
  }

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response) throws IOException {
    Map<String, Object> pageVariables = new HashMap<>();
    pageVariables.put("login", "");
    pageVariables.put("email", "");
    pageVariables.put("password", "");
    pageVariables.put("sessionId", request.getSession().getId());


    response.getWriter().println(PageGenerator.instance().getPage("signup.html", pageVariables));
    response.setContentType("text/html;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);
  }

  public void doPost(HttpServletRequest request,
                     HttpServletResponse response) throws IOException {
    String login = request.getParameter("login");
    String password = request.getParameter("password");
    String email = request.getParameter("email");
    if (accountService.getUserByLogin(login) == null) {
      accountService.addNewUser(new UserProfile(login, email, password));
      response.getWriter().println("Signing up successfully");
    }else {
      System.out.println("Such user is already exists");
    }
  }
}
