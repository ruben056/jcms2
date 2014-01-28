package rd.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rd.mgr.layout.BasicLayoutEngine;
import rd.mgr.layout.DefaultLayoutEngine;
import rd.mgr.layout.ILayoutEngine;
import rd.mgr.layout.Layout;
import rd.mgr.page.Page;
import rd.util.ComponentFactory;
import rd.util.StringUtil;
import rd.util.db.DBUtil;

/**
 * This servlets retrieves a page, and renders the output.
 * 
 * Servlet implementation class RenderServlet
 */
@WebServlet(name="RenderServlet", urlPatterns={"/public"})
public class RenderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RenderServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = null;
		
		EntityManager eMgr = DBUtil.initEmgr();
		eMgr.getTransaction().begin();
		try {
			result = performAction(request, response);
			eMgr.getTransaction().commit();
		} catch (Exception e) {
			e.getCause().printStackTrace();
			result = e.getLocalizedMessage();
			eMgr.getTransaction().rollback();
		} finally{
			response.getWriter().write(result);
			response.setContentType("text/html");
			eMgr.close();
		}
	}
	
	public String performAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, Exception{
		
		ILayoutEngine layoutEngine = getLayoutEngine();
		
		Page p = getPage( req, resp);		
		String template = getTemplate( p);
		
		layoutEngine.setPage(p);
		layoutEngine.setTemplate(template);
		return layoutEngine.toHTML();
	}
	
	private ILayoutEngine getLayoutEngine(){
		
		Layout sel = ComponentFactory.getLayoutMgr().getSelectedLayout()[0];
		ILayoutEngine result = null;
		ILayoutEngine[] layoutEngines = new ILayoutEngine[]{new DefaultLayoutEngine(), new BasicLayoutEngine()};
		for (int i = 0; i < layoutEngines.length; i++) {
			ILayoutEngine cur = layoutEngines[i];
			if(cur.isLayoutEngineFor(sel.getName())){
				result = cur;
				break;
			}
		}
		
		return result;
	}
	
	private String getTemplate( Page p){
		String template = getServletContext().getContextPath()+"/themes/basic/html/" + 
				(StringUtil.isNull(p.getTemplate())?"default":p.getTemplate()) 
						+ ".html";
		// TODO if not exists use default template anyway
		
		return template;
	}
	
	/**
	 * Retrieve page:
	 * 1. if id retrieve by id
	 * 2. if name retrieve by name
	 * 3. if no param is given retrieve home page
	 * @return
	 */
	private Page getPage( HttpServletRequest req, HttpServletResponse resp){
		Page p = null;
		String o = req.getParameter("id");
		if(o != null){
			long id = Long.parseLong(o);
			p = ComponentFactory.getPageMgr().getPageById( id);
			if(p==null){
				//TODO throw some kind of error
			}
		}else if((o = req.getParameter("name")) != null){			
			Page[] pages = ComponentFactory.getPageMgr().getPageByName( o);
			if(pages != null && pages.length > 0){
				p = pages[0];
			}else{
				//TODO throw some kind of error
			}
		}else{
			Page[] home = ComponentFactory.getPageMgr().getHomePage();
			if(home != null && home.length > 0){
				p = home[0];
			}
		}
		
		return p;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().write("did not expect the post to be triggered here????");
	}

}
