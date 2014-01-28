package rd.util.widget;

import javax.servlet.ServletException;

import org.apache.commons.lang.StringEscapeUtils;

import rd.util.GeneralUtil;
import rd.util.widget.plugin.Plugin;
import rd.util.widget.plugin.PluginFactory;

public class WidgetFactory {

	// custom widgets created by users, are retrieved from config files
	
	// system/standard widgets go here as static String,
	public static String RD_MENU = "rdMenu";
	public static String RD_STICKY_FOOTER = "rdStickyFooter";

	/**
	 * 
	 * @param jsonStringWitName: name + json representation of the widget example: rdMenu{'direction':'0', 'opacity':'1'}
	 */
	public static IWidget createWidget(String jsonStringWithName) throws Exception{
		
		String name = "";
		String atts = null;
		if(jsonStringWithName.indexOf('{') > 1){
			name = jsonStringWithName.substring(0, jsonStringWithName.indexOf('{'));
			atts = jsonStringWithName.substring(jsonStringWithName.indexOf('{'));
		}else{
			name = jsonStringWithName;
			atts = null;;
		}

		IWidget result = null;
		
		if(WidgetFactory.RD_MENU.equals(name)){
			if(atts == null){
				result = new RdMenu();
			}else {
				result = GeneralUtil.getGSON().fromJson(StringEscapeUtils.unescapeHtml(atts), RdMenu.class);
			}
		}else if(WidgetFactory.RD_STICKY_FOOTER.equals(name)){
			if(atts == null){
				result = new RDStickyFooter();
			}else {
				result = GeneralUtil.getGSON().fromJson(StringEscapeUtils.unescapeHtml(atts), RDStickyFooter.class);
			}
		}else{
			System.out.println("Checking plugins for widget with name:" + name);
			Plugin p = PluginFactory.getPluginForWidget(name);
			
			if(p == null){
				System.out.println("No widget with the name : " + name +" exists!!");
				throw new ServletException("No widget with the name : " + name +" exists!!");
			}
			result = (IWidget)GeneralUtil.getGSON().fromJson(StringEscapeUtils.unescapeHtml(atts), 
					Class.forName(p.getWidgetClass()));
		}		
		
		return result;
	}
}
