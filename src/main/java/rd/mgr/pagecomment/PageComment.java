package rd.mgr.pagecomment;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import rd.mgr.page.Page;

@Entity
@Table(name="pageComments")
@NamedQueries({
    @NamedQuery(name="pageComments.findAll",
                query="SELECT pc FROM PageComment pc"),
    @NamedQuery(name="pageCommentsForPage", 
    			query="from PageComment p where p.page =:page and status = 1")
}) 
public class PageComment {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
	private long id;   		// not nulllable auto generated, autoincerement
	private String text;
	private String authorName;
	private String authorEmail;
	private String authorWebsite;
	private int status;
	private Date cDate;
	
	@ManyToOne
	@JoinColumn(name="page_id")
	private Page page;
	
	
	public PageComment(){}
	
	public PageComment(String text, String authorName, String authorEmail
			, String authorWebsite, Page p){
		this.text = text;
		this.authorName = authorName;
		this.authorEmail = authorEmail;
		this.authorWebsite = authorWebsite;
		this.page = p;
		this.status = 1;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getAuthorEmail() {
		return authorEmail;
	}
	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}
	public String getAuthorWebsite() {
		return authorWebsite;
	}
	public void setAuthorWebsite(String authorWebsite) {
		this.authorWebsite = authorWebsite;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getcDate() {
		return cDate;
	}
	public void setcDate(Date cDate) {
		this.cDate = cDate;
	}
}
