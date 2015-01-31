package com.harm;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class MainServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		herdinLogging hl = new herdinLogging(this);
		hl.ENTER();

//		System.out.println(req.getRequestURI());
		
		if(req.getRequestURI().contains("imca")) {
			resp.sendRedirect("/insaneMonthlyCommentAdder.HTML");
		} else {
			resp.sendRedirect("/index.html");
		}

		
		hl.LEAVE();
	}//END OF doGet()
}//END OF CLASS