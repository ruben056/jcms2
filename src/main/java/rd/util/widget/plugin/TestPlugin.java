package rd.util.widget.plugin;

import java.util.Vector;

import rd.util.widget.IWidget;

/**
 * @usage [[jCMS:testPlugin{"title":"bla bla titel", "divContent":"div contents"}]]
 * 
 * 
 * @author ruben
 *
 */
public class TestPlugin implements IWidget {

	private final static String REPLACE_PATTERN = "\\[\\[jCMS:testPlugin(.*?)\\]\\]";
	
	private String title;
	private String divContent;
	
	@Override
	public String getReplacePattern() {
		return TestPlugin.REPLACE_PATTERN;
	}

	@Override
	public String toHTML() {
		StringBuilder sb = new StringBuilder("<h1>" + getTitle() +"</h1>");
		sb.append("<div>");
		sb.append("<p>" + getDivContent() +"</p>");
		sb.append("</div>");
		return sb.toString();
	}

	
	
	public String getDivContent() {
		return divContent;
	}

	public void setDivContent(String divContent) {
		this.divContent = divContent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean isOnePerPage() {
		return false;
	}

	@Override
	public Vector<String> addStylesAndScripts(Vector<String> stylesAndScripts) {
		return stylesAndScripts;
	}

}
