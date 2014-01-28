package rd.mgr.layout;

import java.util.Iterator;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.primitives.ArrayLongList;
import org.apache.commons.collections.primitives.LongList;

import rd.util.db.DBUtil;

public class LayoutMgr implements ILayoutMgr {
	
	@Override
	public Layout[] saveLayouts(Layout[] layouts) {
		if (layouts == null || layouts.length == 0) {
			return null;
		}

		LongList ll = new ArrayLongList();		
		for (int i = 0; i < layouts.length; i++) {
			Layout cur = layouts[i];
			if (cur.getId() <= 0) {
				DBUtil.getEntityMgr().persist(cur);
			} else {
				DBUtil.getEntityMgr().merge(cur);
			}
			if(cur.isEnabled())
				ll.add(cur.getId());
		}
		
		Layout[] selected = getSelectedLayout();
		if(selected.length > 0){
			if(selected.length != 1){
				short s = 0;
				for (int i = 0; i < selected.length; i++) {
					Layout cur = selected[i];
					if(ll.contains(cur.getId()) && s==0){
						s=1;
						continue;
					}
					if(s==0 && i==selected.length-1){
						break;
					}
					cur.setEnabled(false);
				}
			}
		} else{
			layouts[0].setEnabled(true); // at least one layout must be selected
		}
	
		
		return layouts;
	}

	@Override
	public Layout[] getSelectedLayout() {
		TypedQuery<Layout> qry = DBUtil.getEntityMgr().createNamedQuery("layout.findSelected", Layout.class);
		return convertToArr(qry.getResultList());
	}
	
	@Override
	public Layout[] getAllLayouts() {
		TypedQuery<Layout> qry = DBUtil.getEntityMgr().createNamedQuery("layout.findAll", Layout.class);
		return convertToArr(qry.getResultList());
	}

	private Layout[] convertToArr(List<Layout> sites) {
		Layout[] result = new Layout[sites.size()];
		Iterator<Layout> it = sites.iterator();
		for(int i = 0; it.hasNext(); i++){
			result[i] = it.next();
		}
		return result;
	}
	
	@Override
	public Layout getLayoutByID(long id){
		return DBUtil.getEntityMgr().find(Layout.class, id);
	}
}
