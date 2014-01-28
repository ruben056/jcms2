package rd.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rd.servlet.ActionServlet;
import rd.servlet.JSonResult;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet(name="LogoutServlet", urlPatterns={"/users/logout"})
public class LogoutServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	protected JSonResult performAction(HttpServletRequest req,
			HttpServletResponse resp)
			throws IOException , ServletException{
		
		JSonResult res = new JSonResult();
		
		HttpSession sess = req.getSession();
		sess.invalidate();
		res.addMsg("user logged out successfully");
		
		return res;
	}
}