package rd.mgr.layout;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="layout")
@NamedQueries({
    @NamedQuery(name="layout.findAll",
                query="SELECT layout FROM Layout layout"),
    @NamedQuery(name="layout.findSelected",
                query="SELECT layout FROM Layout layout where enabled = 1")                
}) 
public class Layout {
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
	private long id;   		// not nulllable auto generated, autoincerement
	private String name;
	private boolean enabled;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
