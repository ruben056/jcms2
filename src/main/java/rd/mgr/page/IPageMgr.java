package rd.mgr.page;





public interface IPageMgr {

	public Page[] getAvailableParentPages( long parentID, long pageID);
	
	public Page[] getPagesForParent( long parentID);
	
	public Page savePage( Page page);
	
	public Page[] savePages( Page[] pages);
	
	public Page getPageById( long id);
	
	public void deletePage( long id);
	
	public Page[] getPageByName( String name);
	
	public Page[] getHomePage();
	
	public Page[] getHomePageAndNot( int pageID);
}
