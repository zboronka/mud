import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Characters extends HttpServlet {
	final String[] stat_names = {"Strength", "Dexterity", "Constitution", "Wisdom", "Intelligence", "Charisma"};
	final String max_query = "SELECT MAX(ID) AS Max FROM Characters";
	Connection conn;

	@Override
	public void doGet(HttpServletRequest request,
	                  HttpServletResponse response)
		throws IOException, ServletException 
	{
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

		String[] uri = request.getRequestURI().split("/");
		String id = uri[uri.length-1];

		try {
			conn = connectDB("com.mysql.cj.jdbc.Driver", "root", "root", "jdbc:mysql://localhost:2837/mudDB");

			String select = "SELECT * FROM Characters";
			Statement statement = conn.createStatement();
			ResultSet rs;

			if(!id.equals("characters")) {
				select += " WHERE ID = " + id;
				rs = statement.executeQuery(select);
				if(!rs.next()) {
					response.setStatus(404);
					out.println(select);
				} else {
					out.println(rs.getString("Name"));
				}
			} else {
				rs = statement.executeQuery(select);
				while(rs.next()) {
					out.println(rs.getString("Name"));
				}
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
			response.setStatus(500);
			out.println(ex.getMessage());
		} catch(ClassNotFoundException ex) {
			ex.printStackTrace();
			response.setStatus(500);
			out.println("Could not find class");
		} finally {
			disconnectDB(conn);
		}
    }

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

		try {
			conn = connectDB("com.mysql.cj.jdbc.Driver", "root", "root", "jdbc:mysql://localhost:2837/mudDB");

			String name = request.getParameter("name");
			if(name == null) {
				response.setStatus(400);
				out.println("Name required");
				return;
			}

			Statement statement = conn.createStatement();

			ResultSet rs = statement.executeQuery(max_query);
			rs.next();

			StringBuffer insert = new StringBuffer("INSERT INTO Characters(ID,Name");

			int id = rs.getInt("Max") + 1;
			Integer[] stat_values = new Integer[6];
			for(int i = 0; i < 6; i++) {
				String stat = request.getParameter(stat_names[i]);

				if(stat != null) {
					stat_values[i] = Integer.parseInt(stat);
					insert.append("," + stat_names[i]);
				}
			}
			insert.append(") VALUES (" + id + ",'" + name + "'");
			for(int i = 0; i < 6; i++) {
				insert.append(stat_values[i] != null ? "," + stat_values[i] : "");
			}
			insert.append(")");

			statement.execute(insert.toString());
			response.setStatus(201);
			response.addHeader("Location", getBaseURL(request) + request.getServletPath() + "/" + id); 
		} catch(SQLException ex) {
			ex.printStackTrace();
			response.setStatus(500);
			out.println(ex.getMessage());
		} catch(ClassNotFoundException ex) {
			ex.printStackTrace();
			response.setStatus(500);
			out.println("Could not find class");
		} finally {
			disconnectDB(conn);
		}
	}

	public static String getBaseURL(HttpServletRequest req) {
		String scheme = req.getScheme();             // http
		String serverName = req.getServerName();     // hostname.com
		int serverPort = req.getServerPort();        // 80
		String contextPath = req.getContextPath();   // /mywebapp
		String servletPath = req.getServletPath();   // /servlet/MyServlet
		String pathInfo = req.getPathInfo();         // /a/b;c=123
		String queryString = req.getQueryString();   // d=789

		// Reconstruct original requesting URL
		StringBuilder url = new StringBuilder();
		url.append(scheme).append("://").append(serverName);

		if (serverPort != 80 && serverPort != 443) {
			url.append(":").append(serverPort);
		}

		url.append(contextPath);
		return url.toString();
	}

	private Connection connectDB(String driver, String user, String pass, String db) throws SQLException, ClassNotFoundException {
		Connection conn = null;

		Class.forName(driver);

		String userName = user;
		String password = pass;
		String url = db;

		conn = DriverManager.getConnection(url, userName, password);

		return conn;
	}

	private void disconnectDB(Connection conn) {
		if (conn != null) {
			try {
				conn.close ();
			} catch (Exception ex) {
				System.err.println ("Error in connection termination");
				ex.printStackTrace();
			}
		}
	}
}
