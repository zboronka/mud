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

public class Characters extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException 
	{
		ServletContext context = this.getServletContext();

        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

		String[] uri = request.getRequestURI().split("/");
		String id = uri[uri.length-1];

		HashMap<Integer, Char> characters;
		if(context.getAttribute("characters") == null) {
			response.setStatus(404);
			return;
		}

		characters = (HashMap<Integer, Char>)context.getAttribute("characters");

		if(!id.equals("characters")) {
			Integer iid = Integer.parseInt(id);
			if(!characters.containsKey(iid)) {
				response.setStatus(404);
			} else {
				out.println(characters.get(iid).name);
			}
		} else {
			for(Entry<Integer,Char> e : characters.entrySet()) {
				out.println(e.getValue().name);
			}
		}
    }

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		HashMap<Integer, Char> characters;
		ServletContext context = this.getServletContext();
		if(context.getAttribute("characters") == null) {
			characters = new HashMap<>();
			context.setAttribute("characters", characters);
		} else {
			characters = (HashMap<Integer, Char>)context.getAttribute("characters");
		}

		String name = request.getParameter("name");
		int STR = request.getParameter("STR") == null ? 8 : Integer.parseInt(request.getParameter("STR"));
		int DEX = request.getParameter("DEX") == null ? 8 : Integer.parseInt(request.getParameter("DEX"));
		int CON = request.getParameter("CON") == null ? 8 : Integer.parseInt(request.getParameter("CON"));
		int WIS = request.getParameter("WIS") == null ? 8 : Integer.parseInt(request.getParameter("WIS"));
		int INT = request.getParameter("INT") == null ? 8 : Integer.parseInt(request.getParameter("INT"));
		int CHA = request.getParameter("CHA") == null ? 8 : Integer.parseInt(request.getParameter("CHA"));

		int id = characters.size();
		characters.put(id, new Char(name, STR, DEX, CON, WIS, INT, CHA));

        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
		out.println(id);
	}
}
