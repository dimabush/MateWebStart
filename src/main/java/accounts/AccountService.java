package accounts;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
  private Map<String, UserProfile> usersDB;
  private Map<String, UserProfile> httpSessionsDB;

  public AccountService() {
    usersDB = new HashMap<>();
    httpSessionsDB = new HashMap<>();
  }

  public void addNewUser(UserProfile userProfile){
    usersDB.put(userProfile.getLogin(), userProfile);
  }

  public UserProfile getUserByLogin(String login){
    return usersDB.get(login);
  }

  public void loginUser(String session, UserProfile user){
    httpSessionsDB.put(session, user);
  }

  public boolean userIsLoggedIn(String session){
    return httpSessionsDB.containsKey(session);
  }

  public String closeUserSession(String session){
    String userLogin = httpSessionsDB.get(session).getLogin();
    httpSessionsDB.remove(session);
    return  userLogin;
  }
}
