package main;

import accounts.AccountService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.MirrorRequestServlet;
import servlets.SignInServlet;
import servlets.SignUpServlet;

public class Main {
  public static void main(String[] args) throws Exception {
    MirrorRequestServlet mirrorRequestServlet = new MirrorRequestServlet();
    AccountService accountService = new AccountService();
    SignInServlet signInServlet = new SignInServlet(accountService);
    SignUpServlet signUpServlet = new SignUpServlet(accountService);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.addServlet(new ServletHolder(mirrorRequestServlet), "/mirror");
    context.addServlet(new ServletHolder(signUpServlet), "/signup");
    context.addServlet(new ServletHolder(signInServlet), "/signin");

    Server server = new Server(8080);
    server.setHandler(context);

    server.start();
    System.out.println("Server started");
    server.join();
  }
}
