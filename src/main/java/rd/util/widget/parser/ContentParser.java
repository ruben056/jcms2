package rd.util.widget.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rd.util.widget.IWidget;
import rd.util.widget.WidgetFactory;

public class ContentParser {

	private final static String SEARCH_PATTERN = "\\[\\[jCMS:(.*?)\\]\\]";

	/**
	 * It parses the string for the first widget pattern it comes across
	 * When it is found it is replaced by its widgets html.
	 * With this new string this logic is repeated untill all widgets are replaced in the content
	 * 
	 * @param eMgr
	 * @param content
	 * @return
	 */
	public static String parse(String content, java.util.Vector<String> stylesAndScripts) throws Exception {

		String result = content;
		Pattern p = Pattern.compile(ContentParser.SEARCH_PATTERN);
		Matcher m = p.matcher(content);
		if(m.find()){
			IWidget w = WidgetFactory.createWidget(m.group(1));
			w.addStylesAndScripts(stylesAndScripts);
			String tmp = content.replaceFirst(w.getReplacePattern(), w.toHTML());
			result = ContentParser.parse(tmp, stylesAndScripts);
		}
		return result; 
	}
}
