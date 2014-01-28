package rd.servlet.pagecomment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rd.mgr.page.Page;
import rd.mgr.pagecomment.PageComment;
import rd.servlet.ActionServlet;
import rd.servlet.JSonResult;
import rd.util.ComponentFactory;
import rd.util.GeneralUtil;
import rd.util.StringUtil;

/**
 * This goes with the pageComment plugin (when using plugin this would be in another jar but hey ...)
 * 
 * @author ruben
 *
 */
@WebServlet(name="PageCommentController", urlPatterns={"/pagecomment/controller"})
public class PageCommentController extends ActionServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.getWriter().write("PageController online");
	}
	@Override
	protected JSonResult performAction(HttpServletRequest req,
			HttpServletResponse resp)
			throws IOException, ServletException {

		JSonResult result = new JSonResult();
		String action = getAction(req);
		
		if(action == null){
			result.addError("No action supplied");
		}else if("saveComment".equals(action)){
			result.setObj(saveComments( req, resp));
			result.addMsg("comments saved successfully");
		}else if("getCommentForPage".equals(action)){
			result.setObj(getCommentsForPage( req, resp));
			result.addMsg("comments retrieved successfully");
		}
		
		return result;
	}

	private PageComment[] getCommentsForPage(HttpServletRequest req, HttpServletResponse resp){
		String s = req.getParameter("pageID");
		if(StringUtil.isNull(s)){
			return new PageComment[0];
		}
		long pageId = Long.parseLong(s);
		Page p = getPageMgr().getPageById( pageId);
		PageComment[] pcs = ComponentFactory.getPageCommentMgr().getPageCommentsForPage( p);
		return pcs;
	}
	
	private PageComment[] saveComments(HttpServletRequest req, HttpServletResponse resp){
		String o = req.getParameter("object");
		if(StringUtil.isNull(o)){
			return new PageComment[0];
		}
		
		PageComment pc = GeneralUtil.getGSON().fromJson(o, PageComment.class);
		return ComponentFactory.getPageCommentMgr().savePageComments( new PageComment[]{pc});
	}
}
