package rd.servlet.view;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import rd.servlet.ActionServlet;
import rd.servlet.JSonResult;
import rd.util.GeneralUtil;

@WebServlet(name="ViewController", urlPatterns={"/admin/views", "/views"})
public class ViewController extends ActionServlet {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 5352266264436276944L;

	public ViewController() {
	        super();
	 }
	 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.getWriter().write("ViewController online");
	}

	@Override
	protected JSonResult performAction(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		JSonResult result = new JSonResult();
		String view = req.getParameter("name");
		InputStream is = getServletContext().getResourceAsStream(view); //admin/views/userForm.xml
		result.setObj(StringEscapeUtils.unescapeHtml(GeneralUtil.getStringFromInputStream(is)));
		
		return result;
	}
}
