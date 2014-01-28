package rd.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rd.mgr.user.User;
import rd.servlet.ActionServlet;
import rd.servlet.JSonResult;
import rd.util.GeneralUtil;

/**
 * Allows to perform CRUD operations on the users from the admin panel
 */
@WebServlet(name="UserController", urlPatterns={"/admin/users/controller"})
public class UserController extends ActionServlet {
	private static final long serialVersionUID = 1L;
       
    public UserController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	resp.getWriter().write("UserController online");
    }
    
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		super.doPost(req, resp);
	}

	@Override
	protected JSonResult performAction(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		JSonResult result = new JSonResult();
		String action = getAction(req);
		if(action == null){
			// default action??
		}else if(action.equalsIgnoreCase("GetAllGroups")){
			result.setObj(getUserMgr().getAllGroups());
		}else if(action.equalsIgnoreCase("GetUsersForList")){
			result.setObj(this.getUsersFromList( req));
		}else if(action.equalsIgnoreCase("delete")){
			this.deleteUser( req, resp);
			result.setObj("");
			result.addMsg("user deleted succesfully");
		}else if(action.equalsIgnoreCase("GetByID")){
			result.setObj(this.getUserById( req, resp));
		}else if(action.equalsIgnoreCase("Save")){
			result.setObj(this.saveUser( req, resp));
		}else{
			result.addMsg("The action " + action + " is not implemented on the servlet " + this.getClass().getName());
		}
		
		return result;
	}

//	---------------------------------------------------------
	
	private User saveUser( HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String json = req.getParameter("object");
		User u = GeneralUtil.getGSON().fromJson(json, User.class);
		return getUserMgr().saveUser( u);
	}
	
	private User getUserById( HttpServletRequest req, HttpServletResponse resp) throws IOException{
		long id = Long.parseLong(req.getParameter("id"));
		return getUserMgr().getUserByID( id);
	}
	
	private void deleteUser( HttpServletRequest req, HttpServletResponse resp) throws IOException{
		long userID = Long.parseLong(req.getParameter("id"));
		getUserMgr().deleteUser( userID);
	}
	
	private User[] getUsersFromList( HttpServletRequest req) throws IOException{
		return getUserMgr().getAllUsers( );
	}
}
