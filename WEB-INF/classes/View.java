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

		String uri = request.getServletPath();

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
}
