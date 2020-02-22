import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {
	class User {
		String password;
		boolean online;
		public User(String password, boolean online) {
			this.password = password;
			this.online = online;
		}
	}

	private HashMap<String,User> users;

	@Override
	public void init() 
		throws ServletException 
	{
		users = new HashMap<>();
		users.put("Bob", new User("password", false));
	}

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException 
	{
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
		for(Map.Entry<String,User> entry : users.entrySet()) {
			if(entry.getValue().online) {
				out.println(entry.getKey());
			}
		}
    }

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		User user = users.get(request.getParameter("username"));
		String pass = request.getParameter("password");
		if(user != null && pass != null && user.password.compareTo(pass) == 0) {
			user.online = true;
		}
	}
}
