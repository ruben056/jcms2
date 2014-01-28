package rd.util.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class RDProperties {

//	public static String GENERAL_JCMSROOT = "jcmsroot";
	public static String ABSOLUTE_UPLOAD_FOLDER = "content.uploadfolder";
	public static String RELATIVE_UPLOAD_FOLDER = "relative.uploadfolder";
	
	private static Properties props = null;
	
	static{
		if(props == null){
			props = new Properties();
			try {
				InputStream is = RDProperties.class.getClassLoader().getResourceAsStream("/jcms.properties");
				props.load(new InputStreamReader(is));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	} 
	
	public static String getString(String prop){
		return props.getProperty(prop);
	}
	
	public static int getInt(String prop) throws Exception{
		String s = props.getProperty(prop);
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new Exception("The property " + prop + " must be numerical value!");
		}
	}
}
