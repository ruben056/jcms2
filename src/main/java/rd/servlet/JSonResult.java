package rd.servlet;

import java.util.Vector;

public class JSonResult {

	private Object obj = null;
	private Vector<String> msgs = null;
	private Vector<String> error = null;
	
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Vector<String> getMsg() {
		return msgs;
	}
	public void setMsg(Vector<String> msg) {
		this.msgs = msg;
	}
	public void addMsg(String s){
		if(this.msgs == null)
			this.msgs = new Vector<String>();
		if(s != null && s.length() > 0){
			this.msgs.add(s);
		}
	}
	
	public Vector<String> getError() {
		return error;
	}
	public void setError(Vector<String> error) {
		this.error = error;
	}
	public void addError(String err){
		if(this.error == null)
			this.error = new Vector<String>();
		if(err != null && err.length() > 0){
			this.error.add(err);
		}
	}
}
