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
import rd.util.ImageUtil;
import rd.util.db.DBUtil;

/**
 * Servlet implementation class RDBrowser
 * Lists the files in the upload folder, TODO must be extended to allow selecting a file ...
 * 
 * Returns thumbnail list of images using data-uris
 */
@WebServlet(name = "RDBrowser", urlPatterns = {"/admin/browser/browse"})
public class RDBrowser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RDBrowser() {
        super();       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StringBuffer sb = new StringBuffer("<html><body><ul>");
		String callBackFnct = request.getParameter("CKEditorFuncNum");
		String imgUrl = request.getServletContext().getContextPath() + "/getImage?id=";
		
		EntityManager eMgr = DBUtil.initEmgr();
		eMgr.getTransaction().begin();
		try {
			Image[] images = ComponentFactory.getImgMgr().getAllImages();
			for (int i = 0; i < images.length; i++) {
				Image cur = images[i];
				byte[] thumbNail = ImageUtil.resizeImg(cur.getImageData());
				String base64String = ImageUtil.encodeToString(thumbNail);
				
				String mimeType = getServletContext().getMimeType(cur.getName().toLowerCase());
				String img = "<img id='"+cur.getId()+"' src='data:" + mimeType + ";base64," + base64String + "' style='cursor:pointer;' alt='"+ cur.getName() +"' />";		
				
				sb.append("<li style='float:left; list-style:none; padding : 5px;'>");
				sb.append(img);
//				sb.append("<span style='display:block;'>"+curURL+ "</span>");
				sb.append("</li>");
			}
			eMgr.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			eMgr.getTransaction().rollback();
		} finally{
			eMgr.close();
		}
		
		sb.append("</ul>");
		// some javascript to send url of selected file back to ckeditor
		sb.append("\n<script	src='http://code.jquery.com/jquery-1.9.1.js'></script>\n");
		sb.append("<script>\n");
		sb.append("jQuery(function() {\n");
		sb.append("var $img = jQuery('ul li img');\n");
		sb.append("$img.click(function(){\n");
		sb.append("var id = $(this).attr('id');\n");
		sb.append("var url = '" + imgUrl  + "' + id;\n");
		sb.append("selectFile(url, " + callBackFnct  + ");\n");
		sb.append("});\n");
		sb.append("function selectFile(url, callBackFnct) {");
		sb.append("window.opener.CKEDITOR.tools.callFunction(callBackFnct, url);");
		sb.append("window.close()");
		sb.append("};");
		sb.append("});\n");
		sb.append("</script>\n");
		
		sb.append("</body></html>");
		
		response.setContentType("text/html");
		response.getWriter().write(sb.toString());
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write("did not expect post here ...");
	}

}
