package rd.mgr.page;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.persistence.TypedQuery;

import rd.util.db.DBUtil;

public class PageMgr implements IPageMgr {

	@Override
	public Page savePage( Page page) {
		return savePages(new Page[]{page})[0];
	}

	/**
	 * When saving make sure there is always exactly 1 page marked as homepage.
	 */
	@Override
	public Page[] savePages( Page[] pages) {
		
		
		if(pages == null || pages.length == 0){
			return new Page[0];
		}
		
		Vector<Long> updateHomeIDs = new Vector<Long>(); 
		for (int i = 0; i < pages.length; i++) {
			Page page = pages[i];
			if(page.getId() <= 0){
				DBUtil.getEntityMgr().persist(page);
			}else{
				DBUtil.getEntityMgr().merge(page);
			}
			if((page.getSpecial()&1) == 1)
				updateHomeIDs.add(page.getId());
		}
		
		/* check for exactly one home page, if multiple choose one of the new ones, if none make one of the new ones homepage */
		Page[] homes = getHomePage();
		if(updateHomeIDs.size() > 0){
			if(homes.length == 0){
				pages[0].setSpecial(pages[0].getSpecial()|1);
			}else if(homes.length > 1){
				boolean foundHome = false;
				for (int i = 0; i < homes.length; i++) {
					Page cur = homes[i];
					if(!foundHome && Arrays.binarySearch(updateHomeIDs.toArray(new Long[updateHomeIDs.size()]), cur.getId()) >= 0){
						foundHome = true;
					}else if(i+1 < homes.length || foundHome){
						cur.setSpecial(cur.getSpecial()&0);
						DBUtil.getEntityMgr().merge(cur);
					}
				}
			}
		}else{
			pages[0].setSpecial(pages[0].getSpecial()|1);
			DBUtil.getEntityMgr().merge(pages[0]);
		}
		
		return pages;
	}

	@Override
	public Page[] getPagesForParent( long parentID) {
		
		TypedQuery<Page> qry = DBUtil.getEntityMgr().createNamedQuery("getPagesForParent", Page.class);
		qry.setParameter("parent", parentID);
		return convertToArr(qry.getResultList());
	}
	
	public Page[] getAvailableParentPages( long parentID, long pageID){
		TypedQuery<Page> qry = DBUtil.getEntityMgr().createNamedQuery("getAvailableParentPages", Page.class);
		qry.setParameter("parent", parentID);
		qry.setParameter("id", pageID);
		return convertToArr(qry.getResultList());
	}

	private Page[] convertToArr(List<Page> pages) {
		Page[] result = new Page[pages.size()];
		Iterator<Page> it = pages.iterator();
		for(int i = 0; it.hasNext(); i++){
			result[i] = it.next();
		}
		return result;
	}
	
	public Page getPageById( long id){
		return DBUtil.getEntityMgr().find(Page.class, id);
	}
	
	@Override
	public void deletePage( long id) {
		Page p = getPageById( id);
		DBUtil.getEntityMgr().remove(p);
	}
	
	@Override
	public Page[] getPageByName( String name) {
		TypedQuery<Page> qry = DBUtil.getEntityMgr().createNamedQuery("getPageByName", Page.class);
		qry.setParameter("name", name);
		return convertToArr(qry.getResultList());
	}

	@Override
	public Page[] getHomePage() {
		 List<Page> pages = DBUtil.getEntityMgr().createNativeQuery(
				 "SELECT * FROM pages where special&1", Page.class).getResultList(); 
		return convertToArr(pages);
	}

	@Override
	public Page[] getHomePageAndNot( int pageID) {
		List<Page> pages = DBUtil.getEntityMgr().createNativeQuery(
				 "SELECT * FROM pages where special&1 and id!="+pageID, Page.class).getResultList(); 
		return convertToArr(pages);
	}
}
