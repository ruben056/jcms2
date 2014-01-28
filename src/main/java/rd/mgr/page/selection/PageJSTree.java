package rd.mgr.page.selection;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

/**
 * This is what the jstree javascript needs
 * for representing the tree structure of pages
 * (serialized in json string)
 * 
 * @author ruben
 */
public class PageJSTree{

	private String data; // pagename
	private Map<String, String> attr; // id
	private long parent; // parentid
	private PageJSTree[] children;
	
	public PageJSTree(long id, String name, long parentID){
		this.data = name;
		this.attr = new HashMap<String, String>();
		this.attr.put("id", String.valueOf(id));
		this.parent = parentID;
	}

	public void setChildren(PageJSTree[] children) {
		this.children = children;
	}
	
	public String toJSON(){
		return new Gson().toJson(this);
	}
}
