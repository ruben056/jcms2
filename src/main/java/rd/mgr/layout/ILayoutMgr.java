package rd.mgr.layout;


public interface ILayoutMgr {

//	public Layout getWebSiteById(EntityManager eMgr, long id);
	
	/**
	 * Save layouts and make sure exactly one layout is  enabled/selected
	 * 
	 * @param eMgr
	 * @param site
	 * @return
	 */
	public Layout[] saveLayouts(Layout[] layouts);
	
	/**
	 * retrieve the enabled/selected layout
	 * 
	 * @param eMgr
	 * @return
	 */
	public Layout[] getSelectedLayout(); 
	
	/**
	 * Retrieve all layouts 
	 * 
	 * @param eMgr
	 * @return
	 */
	public Layout[] getAllLayouts();
	
	public Layout getLayoutByID(long id);
}
