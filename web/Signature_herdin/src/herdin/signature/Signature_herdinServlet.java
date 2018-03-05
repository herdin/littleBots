package herdin.signature;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Signature_herdinServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}//END OF doGet()
}//END OF CLASS