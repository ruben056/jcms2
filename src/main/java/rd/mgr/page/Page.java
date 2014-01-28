package rd.mgr.page;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import rd.util.ComponentFactory;


@Entity
@Table(name="pages")
@NamedQueries({
    @NamedQuery(name="Page.findAll",
                query="SELECT p FROM Page p"),
    @NamedQuery(name="getPagesForParent", 
    			query="from Page p where p.parentID =:parent"),
    @NamedQuery(name="getAvailableParentPages", 
				query="from Page p where p.parentID =:parent and p.id !=:id"),
	@NamedQuery(name="getPageByName", 
				query="from Page p where p.name =:name")
}) 
public class Page {

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
	private long id;   		// not nulllable auto generated, autoincerement
	private long parentID = 0;	// not nullable (is zero by default)
	private String name; // not nullable
	private String title; // not nullable (name by default)
	@Lob
	private String body;
	private String template;
	private int type = 1;
	private String keywords;
	private String description;
	private Date associatedDate;
	private String vars;
	private int ord;  // not nullable 0 by default
	private Date cdate;
	private int special;
	private Date edate;
	
	public Page(){
		
	}
	
	public Page(String name){
		this.name = name;
		this.title = name;
	}
	
	public Page(String name, String title){
		this.name = name;
		this.title = title;
	}

	public long getId() {
		return id;
	}
//	public void setId(long id) {
//		this.id = id;
//	}
	public long getParentID() {
		return parentID;
	}
	public void setParentID(long parentID) {
		this.parentID = parentID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getAssociatedDate() {
		return associatedDate;
	}
	public void setAssociatedDate(Date associatedDate) {
		this.associatedDate = associatedDate;
	}
	public String getVars() {
		return vars;
	}
	public void setVars(String vars) {
		this.vars = vars;
	}
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
	}
	public Date getCdate() {
		return cdate;
	}
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	public int getSpecial() {
		return special;
	}
	public void setSpecial(int special) {
		this.special = special;
	}
	public Date getEdate() {
		return edate;
	}
	public void setEdate(Date edate) {
		this.edate = edate;
	}

	/**
	 * returns a hierarchical url: grandparenurl/parenturl/pageurl
	 */
	@Transient
	private String relativeURL = null;
	public String getRelativeURL(){
		if(this.relativeURL == null){
		
			if(parentID != 0){
				Page parent = ComponentFactory.getPageMgr().getPageById(this.getParentID());
				this.relativeURL = parent.getRelativeURL();
			}else{
				this.relativeURL = "/cms/public"; //this is depending on the context
			}
			
			this.relativeURL = this.relativeURL +"/" + this.getURLSafeName();
			
		}
		return relativeURL;
	}
	@Transient
	private String urlSafeName=null;
	public String getURLSafeName(){
		if(urlSafeName == null){
			urlSafeName = this.name;
			urlSafeName = urlSafeName.replace("/[^a-zA-Z0-9,-]/","-");
		}
		return urlSafeName;
	}
}
