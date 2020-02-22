import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {
	class User {
		String username;
		String password;
		boolean online;
		public User(String username, String password, boolean online) {
			this.username = username;
			this.password = password;
			this.online = online;
		}
	}

	private ArrayList<User> users;

	@Override
	public void init() 
		throws ServletException 
	{
		users = new ArrayList<>();
		users.add(new User("Bob", "password", false));
	}

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException 
	{
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
		out.println("Hello World!");
    }
}
