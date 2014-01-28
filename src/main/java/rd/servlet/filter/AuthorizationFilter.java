package rd.servlet.filter;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rd.mgr.user.Group;
import rd.mgr.user.User;
import rd.util.ComponentFactory;
import rd.util.db.DBUtil;

/**
 * Servlet Filter implementation class AuthorizationFilter
 */
@WebFilter(description="sdf", urlPatterns={"/admin/*", "/admin/pages/controller", "/admin/users/*"} , filterName="AuthorizationFilter")
public class AuthorizationFilter implements Filter {

	private ServletContext ctx = null;
    /**
     * Default constructor. 
     */
    public AuthorizationFilter() {
        
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		EntityManager eMgr = DBUtil.initEmgr();
		eMgr.getTransaction().begin();
		boolean allowed = false;
		try {
//			String url = ((HttpServletRequest)request).getRequestURL().toString();
//			String queryString = ((HttpServletRequest)request).getQueryString();		
			HttpSession session = ((HttpServletRequest)request).getSession();
			
			allowed = checkUserAccount(session);
			
			eMgr.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			eMgr.getTransaction().rollback();
		} finally{
			eMgr.close();
		}
		
		if(!allowed){
			ctx.getRequestDispatcher("/login.jsp").forward(request, response);
		}else{
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		ctx = fConfig.getServletContext();
	}
	
	private boolean checkUserAccount(HttpSession session){
		Object o = session.getAttribute("user");		
		if(o != null && o instanceof User){
			User u = (User)o;
			u = ComponentFactory.getUserMgr().getUserByEmail(u.getEmail());
			java.util.Iterator<Group> it = u.getGroups().iterator();
			while(it.hasNext()){
				Group g = it.next();
				if("_administrator".equals(g.getName())){
					return true;
				}
			}
		}
		return false;
	}
}
