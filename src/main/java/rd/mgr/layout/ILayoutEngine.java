package rd.mgr.layout;

import rd.mgr.page.Page;

public interface ILayoutEngine {

	public boolean isLayoutEngineFor(String layoutName);
	public void setPage(Page p);
	public void setTemplate(String template);
	public String toHTML() throws Exception;
	
}
