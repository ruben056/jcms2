package rd.util.widget;


public class RDHeader extends BaseWidget {

	private final static String REPLACE_PATTERN = "\\[\\[jCMS:rdHeader(.*?)\\]\\]";
	
	
	@Override
	public String getReplacePattern() {
		return RDHeader.REPLACE_PATTERN;
	}

	@Override
	public String toHTML() {
		StringBuilder sb = new StringBuilder("<div id='rdHeader'>");
		
		
		sb.append("</div>");
		return sb.toString();
	}

	@Override
	public boolean isOnePerPage() {
		return true;
	}
}
