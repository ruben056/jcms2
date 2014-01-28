package rd.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rd.mgr.user.User;
import rd.servlet.ActionServlet;
import rd.servlet.JSonResult;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name="LoginServlet", urlPatterns={"/users/login"})
public class LoginServlet extends ActionServlet{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	protected JSonResult performAction(HttpServletRequest req,
			HttpServletResponse resp)
			throws IOException, ServletException {
		
		JSonResult res = new JSonResult();
		
		String email = req.getParameter("email");
		String pass = req.getParameter("pass");
		
		User u = getUserMgr().getUserByEmail( email);
		if(u != null){
			if(pass.equals(u.getPass())){
				//login sucessfull... add session info
				req.getSession().setAttribute("user", u);
				res.addMsg("user logged in");
			}else{
				res.addMsg("Incorrect password");	
			}
		}else{
			res.addMsg("No user with that email address found");
		}
		
		return res;
	}
}
