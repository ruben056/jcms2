package rd.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rd.mgr.page.IPageMgr;
import rd.mgr.user.IUserMgr;
import rd.util.ComponentFactory;
import rd.util.GeneralUtil;
import rd.util.db.DBUtil;

public abstract class ActionServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2695324829929038559L;

	// this should perform check the general user credentials, user must be logged in 
	// and userrole must be admin, maybe some other general stuff...
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		JSonResult result = null;
		
		EntityManager eMgr = DBUtil.initEmgr();
		eMgr.getTransaction().begin();
		try {
			result = performAction(req, resp);
			eMgr.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			eMgr.getTransaction().rollback();
			result = new JSonResult();
			result.addError(e.getLocalizedMessage());
		} finally{
			resp.getWriter().write(GeneralUtil.getGSON().toJson(result));
			eMgr.close();
		}
	}
	
	protected abstract JSonResult performAction(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, ServletException;
	
	protected String getAction(HttpServletRequest req){
		return req.getParameter("action");
	}
	
	protected IUserMgr getUserMgr() {
		return ComponentFactory.getUserMgr();
	}

	protected IPageMgr getPageMgr() {
		return ComponentFactory.getPageMgr();
	}
}
