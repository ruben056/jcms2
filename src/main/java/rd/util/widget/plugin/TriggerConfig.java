package rd.util.widget.plugin;

public class TriggerConfig {
	private String trigger;
	private String action;
	
	public TriggerConfig(){}
	
	public TriggerConfig(String trigger, String action){
		this.trigger = trigger;
		this.action = action;
	}

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public String key(){
		return this.trigger + ":" + this.action;
	}
}
