package rd.mgr.pagecomment;

import rd.mgr.page.Page;

public interface IPageCommentMgr {

	public PageComment[] savePageComments(PageComment[] pcs) ;
	
	public PageComment[] getPageCommentsForPage(Page p);
}
