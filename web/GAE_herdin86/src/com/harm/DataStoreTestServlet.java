package com.harm;	

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class DataStoreTestServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//		herdinLogging.getInstance().ENTER(this);
	
		req.getRequestDispatcher("/index.html").forward(req, resp);
		
		
//		PersistenceManager pm = PersistenceManagerFactory
		/*
		 * PersistenceManager pm = PMF.get().getPersistenceManager(); String
		 * query = "SELECT FROM " + JavaShopBean.class.getName(); try {
		 * List<JavaShopBean> s = (List<JavaShopBean>)
		 * pm.newQuery(query).execute(); if (s.size() > 0)
		 * req.setAttribute("shops", s); String url = "/templates/index.jsp";
		 * try { req.getRequestDispatcher(url).forward(req, resp); } catch
		 * (ServletException e) { e.printStackTrace(); } } finally { pm.close();
		 * } return;
		 */
		
		
//		herdinLogging.getInstance().LEAVE(this);
	}//END OF doGet()
}//END OF CLASS