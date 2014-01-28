package rd.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import rd.mgr.images.IImageMgr;
import rd.mgr.layout.ILayoutMgr;
import rd.mgr.page.IPageMgr;
import rd.mgr.pagecomment.IPageCommentMgr;
import rd.mgr.user.IUserMgr;


/**
 * TODO Will become obsolete when using the spring containser dependency injection..
 * @author ruben
 *
 */
public class ComponentFactory {

	private static ClassPathXmlApplicationContext ctx = 
			new ClassPathXmlApplicationContext("beans.xml");
	
	private static IUserMgr userMgr = null;
	public static IUserMgr getUserMgr(){
		if(userMgr == null){
			userMgr = (IUserMgr)ctx.getBean("UserMgr");
		}
		return userMgr;
	}
	
	private static IPageMgr pageMgr = null;
	public static IPageMgr getPageMgr(){
		if(pageMgr == null){
			pageMgr = (IPageMgr)ctx.getBean("PageMgr");
		}
		return pageMgr;
	}
	
	private static IPageCommentMgr pageCommentMgr = null;
	public static IPageCommentMgr getPageCommentMgr(){
		if(pageCommentMgr == null){
			pageCommentMgr = (IPageCommentMgr)ctx.getBean("PageCommentMgr");
		}
		return pageCommentMgr;
	}
	
	private static ILayoutMgr layoutMgr = null;
	public static ILayoutMgr getLayoutMgr(){
		if(layoutMgr == null){
			layoutMgr = (ILayoutMgr)ctx.getBean("LayoutMgr");
		}
		return layoutMgr;
	}
	
	private static IImageMgr imgMgr = null;
	public static IImageMgr getImgMgr(){
		if(imgMgr == null){
			imgMgr = (IImageMgr)ctx.getBean("ImageMgr");
		}
		return imgMgr;
	}
}
