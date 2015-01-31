package com.harm;	

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class DataStoreTestServlet extends HttpServlet {

	public static int dataId = 1;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//		herdinLogging.getInstance().ENTER(this);
		
//		 PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");
		 
		 Key dataKey = KeyFactory.createKey("dataKey", dataId);
		 String content = "content" + dataId++;
		 Entity dataEntity = new Entity("dataEntity", dataKey);
		 dataEntity.setProperty("date", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		 dataEntity.setProperty("content", content);
		 
		 DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		 dataStore.put(dataEntity);
/*
UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();

    String guestbookName = req.getParameter("guestbookName");
    Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
    String content = req.getParameter("content");
    Date date = new Date();
    Entity greeting = new Entity("Greeting", guestbookKey);
    if (user != null) {
      greeting.setProperty("author_id", user.getUserId());
      greeting.setProperty("author_email", user.getEmail());
    }
    greeting.setProperty("date", date);
    greeting.setProperty("content", content);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(greeting);

    resp.sendRedirect("/guestbook.jsp?guestbookName=" + guestbookName);		 
*/
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