package rd.servlet.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rd.mgr.page.Page;
import rd.mgr.page.selection.GetOptionsForParentCBO;
import rd.mgr.page.selection.GetPageHierarchy;
import rd.servlet.ActionServlet;
import rd.servlet.JSonResult;
import rd.util.GeneralUtil;
import rd.util.ISpecialSelection;

@WebServlet(name="PageController", urlPatterns={"/admin/pages/controller"})
public class PageController extends ActionServlet {


	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.getWriter().write("PageController online");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}
	
	@Override
	protected JSonResult performAction(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		JSonResult result = new JSonResult();
		String action = getAction(req);
		if(action == null){
			// default action??
		}else if(action.equalsIgnoreCase("Update Page Details")
				|| action.equalsIgnoreCase("Insert Page Details")){
			
			result.setObj(updatePageDetails(req, resp));
			
		}else if(action.equalsIgnoreCase("RetrieveNode")){
			ISpecialSelection sel = new GetPageHierarchy();
			result.setObj(sel.performSelection());
			
		}else if(action.equalsIgnoreCase("getPageById")){
			Object tmp = req.getParameter("id");
			if(tmp != null){
				result.setObj(getPageMgr().getPageById(Long.parseLong((String)tmp)));
			}
			
		}else if(action.equalsIgnoreCase("getParents")){
			// TODO Retrieve possible parents for the page
			// --> special selection
			result.setObj(retrieveParents( req, resp));
			
		}else if(action.equalsIgnoreCase("MovePage")){
			movePage( req, resp);
			result.addMsg("The page has been moved.");
			
		}else if(action.equalsIgnoreCase("DeletePage")){
			deletePage( req, resp);
			result.addMsg("The page has been removed.");
		}else{
			result.addMsg("The action " + action + " is not implemented on the servlet " + this.getClass().getName());
		}
		return result;
	}
	
	private void deletePage(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		long id = Long.parseLong((req.getParameter("id")));
		getPageMgr().deletePage( id);
	}
	
	/**
	 * The page gets a new parent, and the attribute that defines the order of the children is redetermined
	 * 
	 * @param eMgr
	 * @param req
	 * @param resp
	 * @return
	 * @throws IOException
	 */
	private void movePage(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		long id = Long.parseLong((req.getParameter("id")));
		long newParent = Long.parseLong((req.getParameter("newparent_id")));
		Page p = getPageMgr().getPageById( id);
		p.setParentID(newParent);
		
		// TODO rearange order of pages in new parent
		Object o = (req.getParameter("order"));
		int[] newOrder = GeneralUtil.getGSON().fromJson((String)o, int[].class);
		for (int i = 0; i < newOrder.length; i++) {
			getPageMgr().getPageById( newOrder[i]).setOrd(i+1);
		}
	}
	
	/**
	 * return list of:
	 * <option value="0"> -- none -- </option>
	 * <option value="pageID"> -- pageName-- </option>
	 * @param eMgr
	 * @param req
	 * @param resp
	 * @return TODO
	 * @throws IOException 
	 */
	private Object retrieveParents(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		GetOptionsForParentCBO sel = new GetOptionsForParentCBO();
		long pageId = 0;
		Object o = req.getParameter("id");
		if(o != null){
			pageId = Long.parseLong((String)o);
		}
		sel.setPageID(pageId);
		return sel.performSelection();
	}
	
	private Page updatePageDetails(HttpServletRequest req, HttpServletResponse resp){
		
		String json = req.getParameter("object");
		Page page = GeneralUtil.getGSON().fromJson(json, Page.class);
		return getPageMgr().savePage( page);
	}
}
