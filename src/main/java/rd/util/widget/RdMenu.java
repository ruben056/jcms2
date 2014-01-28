package rd.util.widget;

import java.util.Vector;

import rd.mgr.page.Page;
import rd.util.ComponentFactory;
import rd.util.ENUM_UTIL;
import rd.util.StringUtil;

public class RdMenu extends BaseWidget{
	
	private final static String REPLACE_PATTERN = "\\[\\[jCMS:rdMenu(.*?)\\]\\]";
	
	private final static String STYLE_HORIZONTAL = "<link rel='stylesheet' href='/cms/css/widgets/menuHorizontal.css'/>";
	private final static String STYLE_VERTICAL= "<link rel='stylesheet' href='/cms/css/widgets/menuVertical.css'/>";
	
	private int direction = ENUM_UTIL.DIRECTION_HORIZONTAL; 
	private long parent = 0; // top level
	private String backGround = "";
	private int columns = 1; // dropdown submenus
	private int opacity = 1; // opacity of the submenus

	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public long getParent() {
		return this.parent;
	}

	public void setParent(long parent) {
		this.parent = parent;
	}

	public String getBackGround() {
		return backGround;
	}

	public void setBackGround(String backGround) {
		this.backGround = backGround;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getOpacity() {
		return opacity;
	}

	public void setOpacity(int opacity) {
		this.opacity = opacity;
	}
	
	
	private String getDirectionString(){
		if(direction == ENUM_UTIL.DIRECTION_HORIZONTAL){
			return "horizontal";
		}else{
			return "vertical";
		}
	}
	
	@Override
	public String toHTML() {
		
		StringBuilder s = new StringBuilder("<div id='menuWrapper'>");
		s.append("<ul id='nav'>");
		s.append(buildList(getParent(), 0));
		s.append("</ul>");
		s.append("</div>");

		//After that, (in your own javascript file) create a new class instance.	
		s.append("<script>" +
				"jQuery(document).ready(function() {" +
				"var myMenu = new MenuMatic({ id:'nav', orientation: '" + getDirectionString() + "'  });"+
				"});"+
				"</script>");
		return s.toString();
	}
	
	
	// TODO add order by here : book page 129
	// retrieve all Pages for a certain parentPage:
	private String buildList(long parentID, int depth){
		StringBuilder sb = new StringBuilder();
	
		Page[] childPages = ComponentFactory.getPageMgr().getPagesForParent(parentID);
		if(childPages.length < 1){
			return "";
		}
		
		if(depth != 0){
			sb.append("<ul");
			StringBuffer style = new StringBuffer(); 
			if(!StringUtil.isNull(getBackGround())){
				style.append("background: ").append(getBackGround()).append(";");
			}
			if(getOpacity() != 1){
				style.append("opacity: ").append(getOpacity()).append(";");
			}
			if(style.length() > 0){
				sb.append(" style=").append(style.toString());
			}
			sb.append(">");
		}
		
		for (int i = 0; i < childPages.length; i++) {
			Page child = childPages[i];
			if((child.getSpecial()&2) == 2) // skip hidden pages
				continue;;
				
			sb.append("<li><a href='" +  child.getRelativeURL()).append("'>").append(child.getName()).append("</a>");
			sb.append(buildList(child.getId(), depth+1));
			sb.append("</li>");
		}
		if(depth != 0)sb.append("</ul>");
		
		return sb.toString();
	}
	
	@Override
	public Vector<String> addStylesAndScripts(Vector<String> stylesAndScripts) {
		if(direction == ENUM_UTIL.DIRECTION_HORIZONTAL){
			stylesAndScripts.add(RdMenu.STYLE_HORIZONTAL);
		}else{
			stylesAndScripts.add(RdMenu.STYLE_VERTICAL);
		}
		stylesAndScripts.add("<script src='http://www.google.com/jsapi' ></script>");
//		stylesAndScripts.add("<script > google.load('mootools', '1.2.1'); </script>");
		stylesAndScripts.add("<script src=''//ajax.googleapis.com/ajax/libs/mootools/1.4.5/mootools-yui-compressed.js'></script>");
		stylesAndScripts.add("<script src='/cms/js/MenuMatic_0.68.3-source.js'></script>");
		stylesAndScripts.add("<script src='/cms/js/widgets/menu.js'></script>");
		return stylesAndScripts;
	}
	
	@Override
	public String getReplacePattern() {
		return RdMenu.REPLACE_PATTERN;
	}

	@Override
	public boolean isOnePerPage() {
		return true;
	}
}