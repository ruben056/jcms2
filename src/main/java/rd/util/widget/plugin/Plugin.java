package rd.util.widget.plugin;


public class Plugin {

	private String name;
	private String description;
	private String version;
	private AdminConfig admin;
	private TriggerConfig[] triggers;
	private String widgetClass;
	private boolean enabled = true; // by default plugins are enabled
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Plugin(){}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public AdminConfig getAdmin() {
		return admin;
	}
	public void setAdmin(AdminConfig admin) {
		this.admin = admin;
	}
	public TriggerConfig[] getTriggers() {
		return triggers;
	}
	public void setTriggers(TriggerConfig[] triggers) {
		this.triggers = triggers;
	}

	public String getWidgetClass() {
		return widgetClass;
	}

	public void setWidgetClass(String widgetClass) {
		this.widgetClass = widgetClass;
	}	
}
