import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class View extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException 
	{
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

		String uri = getBaseURL(request);

		out.println("GET: " + uri + "/characters/<id>");
		out.println("Returns character with id");
		out.println("POST: " + uri + "/characters");
		out.println("Creates character and returns id. Must provide name.");
    }

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		doGet(request, response);
	}

	public static String getBaseURL(HttpServletRequest req) {

		String scheme = req.getScheme();             // http
		String serverName = req.getServerName();     // hostname.com
		int serverPort = req.getServerPort();        // 80
		String contextPath = req.getContextPath();   // /mywebapp
		String servletPath = req.getServletPath();   // /servlet/MyServlet
		String pathInfo = req.getPathInfo();         // /a/b;c=123
		String queryString = req.getQueryString();          // d=789

		// Reconstruct original requesting URL
		StringBuilder url = new StringBuilder();
		url.append(scheme).append("://").append(serverName);

		if (serverPort != 80 && serverPort != 443) {
			url.append(":").append(serverPort);
		}

		url.append(contextPath);/*.append(servletPath);

		  if (pathInfo != null) {
		  url.append(pathInfo);
		  }
		  if (queryString != null) {
		  url.append("?").append(queryString);
		  }
								  */
		return url.toString();
	}
}
