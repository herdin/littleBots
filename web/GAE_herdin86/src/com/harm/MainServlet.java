package com.harm;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class MainServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		Logger logger = Logger.getLogger(this.getClass().getSimpleName());
//		logger.log(Level.INFO, this.getClass().getSimpleName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		
//		System.out.println(req.getRequestURI());
		
		if(req.getRequestURI().contains("imca")) {
			resp.sendRedirect("/insaneMonthlyCommentAdder.HTML");
		} else {
			resp.sendRedirect("/index.html");
		}


	}//END OF doGet()
}//END OF CLASS