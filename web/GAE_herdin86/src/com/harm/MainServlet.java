package com.harm;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.BusinessCardBean;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

@SuppressWarnings("serial")
public class MainServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		Logger logger = Logger.getLogger(this.getClass().getSimpleName());
		logger.log(Level.INFO, "ENTER MAIN SERVLET");
		
		
		if(req.getRequestURI().contains("imca")) {
			res.sendRedirect("/insaneMonthlyCommentAdder.HTML");
//		} else if(req.getRequestURI().contains("chgBC")) {
//			res.sendRedirect("/chgBCPos.html");
		} else {
			Key dataKey = KeyFactory.createKey(BusinessCardBean.BUSINESS_CARD_KIND, BusinessCardBean.BUSINESS_CARD_KEY);
			DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
			Query query = new Query(BusinessCardBean.BUSINESS_CARD_KIND, dataKey);
			query.addSort(BusinessCardBean.REGIST_DATE, SortDirection.DESCENDING);
			List<Entity> results = dataStoreService.prepare(query).asList(FetchOptions.Builder.withLimit(1));
			
			String position = (String)results.get(0).getProperty(BusinessCardBean.BUSINESS_POSITION);
			logger.log(Level.INFO, "SELECT : " + position);
			req.setAttribute("position", position);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/LGCNSnameCard.jsp");
			dispatcher.forward(req, res);
		}

		logger.log(Level.INFO, "LEAVE MAIN SERVLET");
	}//END OF doGet()
}//END OF CLASS