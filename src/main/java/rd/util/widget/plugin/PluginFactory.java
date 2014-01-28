package rd.util.widget.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Vector;

import rd.util.GeneralUtil;
import rd.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Class that caches all plugin information and makes them available for the
 * application to load when required.
 * 
 * @author ruben
 */
public class PluginFactory {

	private static HashMap<String, Plugin> pluginsPerWidget = new HashMap<String, Plugin>();
	private static HashMap<String, Vector<Plugin>> pluginsPerTrigger = new HashMap<String, Vector<Plugin>>();
	private static Vector<Plugin> allPlugins= new  Vector<Plugin>();

//	private static Vector<Plugin> plugins = new Vector<Plugin>();
	static {
		try{
			// plugins are not mandatory so neither is the config ...
	//		String tmp = RDProperties.getString(RDProperties.PLUGIN_DIR);		
			URL url = PluginFactory.class.getClassLoader().getResource("/plugins");
			String tmp = url.getPath();
			if (!StringUtil.isNull(tmp)) {
				File pluginDir = new File(tmp);
				if (pluginDir.exists()) {
					Gson gson = GeneralUtil.getGSON();
					File[] files = pluginDir.listFiles();
					for (int i = 0; i < files.length; i++) {
						File cur = files[i];
						if (cur.isDirectory()) {
							File pluginFile = new File(cur.getAbsolutePath()+"/plugin.json");
							if(pluginFile.exists()){
								try {
									String fileContent = GeneralUtil.readFile(pluginFile);
									Plugin p = gson.fromJson(fileContent, Plugin.class);
									allPlugins.add(p);
									
									// not all plugins are widgets i guess, need to add way to differentiate here ...
									pluginsPerWidget.put(p.getName(), p);
									
									TriggerConfig[] triggers = p.getTriggers();
									for (int j = 0; j < triggers.length; j++) {
										String key = triggers[j].key();
										Vector<Plugin> v = pluginsPerTrigger.get(key);
										if(v==null){
											v = new Vector<Plugin>();
											pluginsPerTrigger.put(key, v);	
										}
										v.add(p);
									}
								} catch (JsonSyntaxException e) {
									System.out.println("The plugin file is not in the correct json format : " + pluginFile.getAbsolutePath());
								} catch (IOException e) {
									System.out.println("The plugin file cannot be read, make sure it is present : " + pluginFile.getAbsolutePath());
								} finally{
									System.out.println("plugins loaded");
								}
							}
						}
					}
				}else{
					System.out
					.println("Plugins are not configured because plugin directory is not configured correct: "
							+ pluginDir.getAbsolutePath());
				}
			}else {
				url = ClassLoader.getSystemResource(".");
				System.out.println("invalid classloader filepath : " + url.getPath());
				
			}
		}catch (Exception e) {
			System.out.println("failed to configure plugins, site will work without plugins");
			e.printStackTrace();
		} 
	}

	public static Plugin getPluginForWidget(String name) {
		return PluginFactory.pluginsPerWidget.get(name);
	}

	public static Vector<Plugin> getPluginForTrigger(String trigger) {
		return PluginFactory.pluginsPerTrigger.get(trigger);
	}
	
	public static Plugin[] getAllPlugins(){
		return allPlugins.toArray(new Plugin[allPlugins.size()]);
	}
}
