package rd.servlet.plugin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rd.servlet.ActionServlet;
import rd.servlet.JSonResult;
import rd.util.widget.plugin.Plugin;
import rd.util.widget.plugin.PluginFactory;

@WebServlet(name="PluginController", urlPatterns={"/admin/plugins/controller"})
public class PluginController  extends ActionServlet {

	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.getWriter().write("PluginController online");
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
		}else if(action.equalsIgnoreCase("GetPlugins")){
			result.setObj(retrievePlugins(req, resp));
			result.addMsg("Plugins retrieved successfully");
		}else if(action.equalsIgnoreCase("disablePlugin")){
			result.setObj(disablePlugin(req, resp));
			result.addMsg("Plugins disabled successfully");
		}else{
			result.addMsg("The action " + action + " is not implemented on the servlet " + this.getClass().getName());
		}
		return result;
	}

	private Plugin[] retrievePlugins(HttpServletRequest req, HttpServletResponse resp) {
		return PluginFactory.getAllPlugins();
	}
	
	/**
	 * TODO
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	private Plugin disablePlugin(HttpServletRequest req, HttpServletResponse resp) {		
		// disable plugin
		return null;
	}
}
