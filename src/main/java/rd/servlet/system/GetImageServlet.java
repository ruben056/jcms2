package rd.servlet.system;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rd.mgr.images.Image;
import rd.util.ComponentFactory;
import rd.util.StringUtil;
import rd.util.db.DBUtil;

@WebServlet(name = "GetImageServlet", urlPatterns = {"/getImage"})
public class GetImageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		
		Image img = null;
		String s = req.getParameter("id");
		
		EntityManager eMgr = DBUtil.initEmgr();
		eMgr.getTransaction().begin();
		try{
			if(StringUtil.isNull(s)){
				s = req.getParameter("name");
				Image[] imgs = ComponentFactory.getImgMgr().getImageByName(s);
				if(imgs.length>0)
					img = imgs[0];
			}else{
				img = ComponentFactory.getImgMgr().getImageByID(Long.valueOf(s));
			}
			
			if(img == null){				
				resp.getWriter().write("TODO add standard image here!!");
				return;
			}
			
			resp.setHeader("Content-Type", getServletContext().getMimeType(img.getName().toLowerCase()));
			resp.setHeader("Content-Disposition", "inline; filename=\"" + img.getName() + "\"");
			resp.getOutputStream().write(img.getImageData());
			
			eMgr.getTransaction().commit();
			
		}catch(Exception e){
			e.printStackTrace();
			eMgr.getTransaction().setRollbackOnly();
		}finally{
			eMgr.clear();
		}
	}
}
