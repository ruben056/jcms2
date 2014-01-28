package rd.mgr.page.selection;

import rd.mgr.page.Page;
import rd.util.ComponentFactory;
import rd.util.ISpecialSelection;

/**
 * 
 * Class that retrieves pages from top to bottom (via parent link)
 * The result is a json format that is required for the jstree javascript used
 * on the front end to represent tree structure of pages
 * 
 * @author ruben
 */
public class GetPageHierarchy implements ISpecialSelection{

	
	@Override
	public Object performSelection() {
		/**
		 * retrieve top level pages
		 * select id,type,name,parent from pages where parent = $id order by ord asc
		 *
		 * recursive function to retrieve the children...
		 */
		Page[] rootPages = ComponentFactory.getPageMgr().getPagesForParent(0);
	
		PageJSTree obj = new PageJSTree(0, "/", -1);
		obj.setChildren(retrieveChildren(rootPages)); // must contain retrieved hierarchy ...
		
		return obj;
	}
	
	private PageJSTree[] retrieveChildren(Page[] rs){
		PageJSTree[] children = new PageJSTree[rs.length];
		
		for (int i = 0; i < rs.length; i++) {
			Page cur = rs[i];
			Page[] subChildren = ComponentFactory.getPageMgr().getPagesForParent(cur.getId());
			children[i] = new PageJSTree(cur.getId(), cur.getName(), cur.getParentID());
			children[i].setChildren(retrieveChildren(subChildren));
		}
		return children;
	}
}