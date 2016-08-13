package com.harm;	

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class chgBCPosServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Logger logger = Logger.getLogger(this.getClass().getSimpleName());
		logger.log(Level.INFO, "ENTER CHANGE BUSINESS CARD SERVLET");
		String position = req.getParameter("position");
		logger.log(Level.INFO, "PARAMETER position VALUE : " + position);
		
		if(position == null || position.length() <= 0)
			return;
		 
		Key dataKey = KeyFactory.createKey(BusinessCardBean.BUSINESS_CARD_KIND, BusinessCardBean.BUSINESS_CARD_KEY);
		Entity entity = new Entity(BusinessCardBean.BUSINESS_CARD_KIND, BusinessCardBean.BUSINESS_CARD_KEY);
		position = position.replaceAll("\n", "<BR>");
		position += "<BR>";
		entity.setProperty(BusinessCardBean.BUSINESS_POSITION, position);
		entity.setProperty(BusinessCardBean.REGIST_DATE, new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREAN).format(new Date()));
		
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		dataStoreService.put(entity);
		
		Query query = new Query(BusinessCardBean.BUSINESS_CARD_KIND, dataKey);
		query.addSort(BusinessCardBean.REGIST_DATE, SortDirection.DESCENDING);
		List<Entity> results = dataStoreService.prepare(query).asList(FetchOptions.Builder.withLimit(1));
		
		logger.log(Level.INFO, "INSERT AND SELECT : " + results.get(0).getProperty(BusinessCardBean.BUSINESS_POSITION));
		logger.log(Level.INFO, "LEAVE CHANGE BUSINESS CARD SERVLET");
	}//END OF doGet()
}//END OF CLASS