package rd.mgr.layout;

import java.util.Iterator;

import rd.mgr.page.Page;
import rd.util.widget.RdMenu;
import rd.util.widget.parser.ContentParser;

/**
 * This layout has header + menu + content + sticky footer
 * 
 * @author ruben
 *
 */
public class BasicLayoutEngine implements ILayoutEngine {

	public static final String LAYOUT_NAME = "BasicLayout" ;
	private Page p ;
	private String template ;
	
	public boolean isLayoutEngineFor(String layoutName){
		return (BasicLayoutEngine.LAYOUT_NAME.equalsIgnoreCase(layoutName));
	}

	@Override
	public void setPage(Page p) {
		this.p = p;
	}

	@Override
	public void setTemplate(String template) {
		this.template = template;
	}

	
	@Override
	public String toHTML() throws Exception {
		
		String metaData = getMetaData(p);
		// TODO for now hardcode to basic css, but should come from template configuration
		metaData += "<link rel='stylesheet' href='/cms/themes/basic/css/main.css' />";
		java.util.Vector<String> stylesAndScripts = new java.util.Vector<String>();
		
		RdMenu theMenu = new RdMenu();
		theMenu.addStylesAndScripts(stylesAndScripts);
		String tmpBody = ContentParser.parse(p.getBody(), stylesAndScripts);
		
		//add widget styles:
		Iterator<String> it = stylesAndScripts.iterator();
		while(it.hasNext()){
			metaData += it.next();
		}
		
		// create html
		String body = "<html><header>" 
				+ metaData
				+"<!--[if !IE 7]><style type='text/css'>#wrap {display:table;height:100%}</style><![endif]-->"
				+"</header><body>";
		
		body += "<div id='wrapper'>";
		body += "<div id='header'></div>";
		body += "<div id='pageWrapper' pageID='"+ p.getId() +"'>";
		body += theMenu.toHTML();
		body += tmpBody;
		body += "</div>";
		body += "</div>";
		
		body += "<div id='footer'>";
		body += "<p>some footer content</p>";
		body += "</div>";
		body += "</body></html>" ;
						
		return body;
	}
	
	private String getMetaData(Page p){
		StringBuffer metaData = new StringBuffer();
		metaData.append("<meta charset=utf-8>");
		metaData.append("<title>").append(
				p.getTitle()== null ? p.getName(): p.getTitle()).append("</title>");
		metaData.append("<script	src='http://code.jquery.com/jquery-1.9.1.js'></script>");
		metaData.append("<script	src='http://code.jquery.com/ui/1.10.3/jquery-ui.js'></script>");
		metaData.append("<link rel='stylesheet' href='http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css'/>");
		metaData.append("<link rel='stylesheet' href='/cms/css/normalize.css' />");
		metaData.append("<link rel='stylesheet' href='/cms/css/main.css' />");
		metaData.append("<link rel='stylesheet' href='/cms/css/basiclayout.css' />");
		if(p.getKeywords() != null && p.getKeywords().length() > 0)
			metaData.append("<meta http-equiv='keywords' content=" + p.getKeywords() +" />");
		return metaData.toString();
	}
}
