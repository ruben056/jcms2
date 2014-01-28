package rd.util.widget.plugin;

import java.util.Vector;

import rd.util.widget.BaseWidget;

public class PageCommentsWidget extends BaseWidget {

	public final static String REPLACE_PATTERN = "\\[\\[jCMS:pageComments(.*?)\\]\\]";
	
	@Override
	public String getReplacePattern() {
		return PageCommentsWidget.REPLACE_PATTERN;
	}

	@Override
	public String toHTML() {
		StringBuilder sb = new StringBuilder("");
		// the content is completely rendered via ajax calls in thejavascript files
		// it retreives a view, then retrieves comments and adds them
		// in that case this method must not be implemented...
		return sb.toString();
	}
	
	@Override
	public Vector<String> addStylesAndScripts(Vector<String> stylesAndScripts) {
		stylesAndScripts.add("<link rel='stylesheet' href='/cms/css/widgets/pageComments.css'/>");
		stylesAndScripts.add("<script src='/cms/js/widgets/pageComments.js' ></script>");
		return stylesAndScripts;
	}
	
	@Override
	public boolean isOnePerPage() {
		return true;
	}
}
