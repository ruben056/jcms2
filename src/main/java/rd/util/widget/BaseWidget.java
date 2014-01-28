package rd.util.widget;

import java.util.Vector;

public abstract class BaseWidget implements IWidget {

	
	@Override
	public boolean isOnePerPage() {
		return false;
	}

	@Override
	public Vector<String> addStylesAndScripts(Vector<String> stylesAndScripts) {
		
		return stylesAndScripts;
	}
}
