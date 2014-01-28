package rd.util.widget;

import java.util.Vector;

/**
 * This is a sticky footer.
 * It as a div that is at the bottom of the page, even if there is no content on the page.
 * If there is alot of content on the page, the footer goes down (no scrollbars for content)
 * 
 * @author ruben
 */
public class RDStickyFooter extends BaseWidget{

	public final static String REPLACE_PATTERN = "\\[\\[jCMS:rdStickyFooter(.*?)\\]\\]";

	private final static String STYLE= "<link rel='stylesheet' href='/cms/css/widgets/rdStickyFooter.css'/>";

	
	@Override
	public String getReplacePattern() {
		return RDStickyFooter.REPLACE_PATTERN;
	}

	@Override
	public String toHTML() {
		StringBuilder sb = new StringBuilder("<div id='wrap'>");
		sb.append("<div id='main' class='clearfix'>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("<div id='rdStickyFooter'>");
		sb.append("Here goes some footer content");
		sb.append("</div>");
		return sb.toString();
	}
	
	@Override
	public Vector<String> addStylesAndScripts(Vector<String> stylesAndScripts) {
		
		stylesAndScripts.add(RDStickyFooter.STYLE);
		
		return stylesAndScripts;
	}
	
	@Override
	public boolean isOnePerPage() {
		return true;
	}
}
