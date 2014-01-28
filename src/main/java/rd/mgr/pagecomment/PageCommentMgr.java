package rd.mgr.pagecomment;

import java.util.Iterator;
import java.util.List;

import javax.persistence.TypedQuery;

import rd.mgr.page.Page;
import rd.util.db.DBUtil;

public class PageCommentMgr implements IPageCommentMgr {

	@Override
	public PageComment[] savePageComments( PageComment[] pcs) {
				
		if(pcs == null || pcs.length == 0){
			return new PageComment[0];
		}
		 
		for (int i = 0; i < pcs.length; i++) {
			PageComment pc = pcs[i];
			if(pc.getId() <= 0){
				DBUtil.getEntityMgr().persist(pc);
			}else{
				DBUtil.getEntityMgr().merge(pc);
			}
		}		
		return pcs;
	}
	
	@Override
	public PageComment[] getPageCommentsForPage( Page p) {
		
		TypedQuery<PageComment> qry = DBUtil.getEntityMgr().createNamedQuery("pageCommentsForPage", PageComment.class);
		qry.setParameter("page", p);
		return convertToArr(qry.getResultList());
	}
	
	private PageComment[] convertToArr(List<PageComment> pcs) {
		PageComment[] result = new PageComment[pcs.size()];
		Iterator<PageComment> it = pcs.iterator();
		for(int i = 0; it.hasNext(); i++){
			result[i] = it.next();
		}
		return result;
	}
}
