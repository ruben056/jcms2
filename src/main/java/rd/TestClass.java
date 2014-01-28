package rd;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import rd.mgr.layout.BasicLayoutEngine;
import rd.mgr.layout.ILayoutMgr;
import rd.mgr.layout.Layout;
import rd.mgr.page.IPageMgr;
import rd.mgr.page.Page;
import rd.mgr.page.selection.GetPageHierarchy;
import rd.mgr.pagecomment.IPageCommentMgr;
import rd.mgr.pagecomment.PageComment;
import rd.mgr.user.Group;
import rd.mgr.user.IUserMgr;
import rd.mgr.user.User;
import rd.util.ComponentFactory;
import rd.util.GeneralUtil;
import rd.util.ISpecialSelection;
import rd.util.db.DBUtil;
import rd.util.widget.RdMenu;
import rd.util.widget.parser.ContentParser;
import rd.util.widget.plugin.Plugin;
import rd.util.widget.plugin.TestPlugin;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class TestClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
//		testJsonParse();
//		testRegExp();
//		testPluginParser();
		EntityManager eMgr = DBUtil.initEmgr(true);		
		eMgr.getTransaction().begin();
		try {
			 /** perform some tests and some initialization here */
			createGroups(eMgr);
			createUser(eMgr);
			testPages(eMgr);
//			testMenu(eMgr);
//			testParse(eMgr);
			
			eMgr.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			eMgr.getTransaction().rollback();
		} finally{
			eMgr.close();
		}
	}
	
	private static void testJsonParse(){
		Gson gson = GeneralUtil.getGSON();
//		gson.fromJson("{'id':'0','authorName':'dd','authorEmail':'demuy@htmail.com','authorWebsite':'d','page':{'id':'1'},'status':'','cDate':''}", PageComment.class);
//		gson.fromJson("{'id':'0','authorName':'dd','authorEmail':'demuy@htmail.com','authorWebsite':'d', 'cDate':''}", PageComment.class);
		gson.fromJson("{'id':'0','authorName':'dd','authorEmail':'demuy@htmail.com','authorWebsite':'d','page':{'id':'1'},'status':'0','cDate':''}", PageComment.class);
		
		
	}
	
	private static void testPluginParser(){
		try {
			Gson gson = new Gson();
			File f = new File("/home/ruben/jCMSRoot/plugins/pageComments/plugin.json");
			String s = new String(Files.readAllBytes(Paths.get(f.toURI())));
			Plugin p = gson.fromJson(s, Plugin.class);
			
			s = "{'title': 'sdfqsfd'}]]";
			TestPlugin tp = gson.fromJson(s, TestPlugin.class);
			System.out.println(tp.getTitle());
			
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void testParse(EntityManager eMgr) throws Exception{
		String toParse = "<html><head></head><body><h1>testing</h1> [[jCMS:rdMenu{'direction':'0'}]] qlmsdkfj lsdfjkqlmdsfkj [[jCMS:rdMenu{'direction':'0', 'opacity':'1'}]]</body></html>";
		
		Vector<String> v = new Vector<String>();
		String res = ContentParser.parse(toParse, v );
		System.out.println(res);
	}
	
	private static void testRegExp(){
		String pattern = "/public[/a-zA-Z0-9-]*/([a-zA-Z0-9-]+)";
		String s= "http://127.0.0.1:8080/cms/public/testPage2/testPage2-child2/pagetest";
		
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(s);
		
		int cnt = m.groupCount();
		System.out.println("groups=" + cnt);
		while(m.find()){
			String grp = m.group(1);
			System.out.println("match = " + grp);
		}
		
	}
	
	private static void testMenu(EntityManager eMgr){		
		RdMenu mnu = new RdMenu();
		String s = mnu.toHTML();
		
		System.out.println("result:\n" + s);;
	}
	
	private static void testPages(EntityManager eMgr){
		
		// create the website first
		Layout layout = new Layout();
		layout.setName("test website"); // (no layout ==> default layout)
		layout.setName(BasicLayoutEngine.LAYOUT_NAME);
		layout = getWebSiteMgr().saveLayouts(new Layout[]{layout})[0];
		
		// create some pages first
		Page[] pages = new Page[4];
		pages[0] = new Page("testPage1", "den titel");
		pages[0].setBody("<h1> blabla bal </h1>");
		pages[0].setParentID(0);
		pages[0].setOrd(1);
		
		pages[1] = new Page("testPage2", "ook root normaal");
		pages[1].setBody("<h1> blabla bal </h1>");
		pages[1].setParentID(0);
		pages[1].setOrd(2);
		
		pages[2] = new Page("testPage2-child1");
		pages[2].setBody("<h1> blabla bal </h1>");
		pages[2].setParentID(2);
		pages[2].setOrd(1);
		
		pages[3] = new Page("testPage2-child2");
		pages[3].setBody("<h1> blabla bal </h1>");
		pages[3].setParentID(2);
		pages[3].setOrd(2);
		
		pages = getPageMgr().savePages(pages);
		GeneralUtil.logObject(pages);
		
		// retrieve hierarchy 
		ISpecialSelection sel = new GetPageHierarchy();
		Object o = sel.performSelection();
		System.out.println("Result of the special selection:");
		if(o == null){
			System.out.println("returned null");
		}else{
			System.out.println("result = " + GeneralUtil.getGSON().toJson(o));
		}
		
		// add commment to page
		PageComment[] pcs = new PageComment[3];
		pcs[0] = new PageComment("1.bla bal comentaar is gemakkelijk e", "van ik", "bla@hotmail.com", "www.minsite.be", pages[0]);		
		pcs[1] = new PageComment("2.bla bal comentaar is gemakkelijk e", "van ik", "bla@hotmail.com", "www.minsite2.be", pages[1]);
		pcs[2] = new PageComment("3.bla bal comentaar is gemakkelijk e", "van ik", "bla@hotmail.com", "www.minsite.be", pages[0]);
		pcs = getPageCommentMgr().savePageComments(pcs);
		GeneralUtil.logObject(pcs);
		
		pcs = getPageCommentMgr().getPageCommentsForPage(pages[0]);
		System.out.println("pcs for first page : ");
		GeneralUtil.logObject(pcs);
	}
	
	private static void createGroups(EntityManager eMgr){
		
		Group[] groups = getUserMgr().getAllGroups();
		if(groups.length > 0){
			System.out.println("Groups allready present:");
			return;					
		}
		
		groups = new Group[]{new Group("_administrator"), new Group("_superadministrator")};
		groups = getUserMgr().saveGroups( groups);
		
		groups = getUserMgr().getAllGroups();
		System.out.println("--curent groups --- ");
		GeneralUtil.logObject(groups);
	}
	
	private static void createUser(EntityManager eMgr) {

		User[] users = getUserMgr().getAllUsers();
		if(users.length > 0){
			System.out.println("there are users in the db ... skip creation of users");
			return;
		}
		
		// create user
		User[] newUsers = new User[]{
				new User("demuynck_ruben@hotmail.com", "zimzimma"),
				new User("rudy@hotmail.com", "zimzimma")};
		Group[] grps = getUserMgr().getAllGroups();
		newUsers[0].addGroup(grps[0]);
		newUsers[0].setActive(true);

		newUsers[1].addGroup(grps[0]);
		newUsers[1].setActive(true);
		
		System.out.println("--- logging before saving: ---");
		GeneralUtil.logObject(newUsers);
		
		// persist user
		getUserMgr().saveUsers(newUsers);
		System.out.println("--- logging after saving: ---");
		GeneralUtil.logObject(newUsers);
		
		// change something  (it should be persisted automatically)
		newUsers[1].setActive(false);
		newUsers[0].addGroup(grps[1]);
	}
	
	
	private static IUserMgr getUserMgr(){
		return ComponentFactory.getUserMgr();
	}
	
	private static IPageMgr getPageMgr(){
		return ComponentFactory.getPageMgr();
	}
	
	private static IPageCommentMgr  getPageCommentMgr(){
		return ComponentFactory.getPageCommentMgr();
	}

	private static ILayoutMgr getWebSiteMgr(){
		return ComponentFactory.getLayoutMgr();
	}
	
//	private static String getBody1(){
//		return "<p>[[jCMS:rdMenu{&#39;direction&#39;:&#39;0&#39; }]]</p>\n\n<p><a href=\"#anker\">#anker</a></p>\n\n<table border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width:500px\">\n\t<tbody>\n\t\t<tr>\n\t\t\t<td>msqdf</td>\n\t\t\t<td>fff</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td>f</td>\n\t\t\t<td>lmj</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td>l</td>\n\t\t\t<td>mmm</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td>sdfsdf</td>\n\t\t\t<td>ffff</td>\n\t\t</tr>\n\t</tbody>\n</table>\n\n<p>qsdfqsdfqsdfqsdfsdf<img alt=\"\" src=\"/cmsUploadFolder/free-computer-wallpapers2-1.jpg\" style=\"float:left; height:188px; margin:5px; opacity:0.9; width:250px\" /></p>\n\n<p>sqfdqdf</p>\n\n<div>\n<p>&micro;&micro;&micro;<img alt=\"\" src=\"/cmsUploadFolder/cubes.jpg\" style=\"float:right; height:113px; margin:20px; width:200px\" /></p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<div>\n<p>&nbsp;</p>\n\n<div>\n<p>anker testje&nbsp;<a id=\"anker\" name=\"anker\"></a></p>\n\n<div lang=\"javascript\" style=\"background:#eee;border:1px solid #ccc;padding:5px 10px;\" title=\"adv titel\">\n<p>nen div met wa rdinformatie</p>\n\n<p>funtion(){</p>\n\n<p>&nbsp;sqldmkfj &nbsp;mslkdf msqkdf qsmkjf</p>\n\n<p>}</p>\n</div>\n\n<p>[[jCMS:pageComments{}]]</p>\n</div>\n\n<p>&nbsp;</p>\n</div>\n</div>\n";
//	}
//	
//	private static String getBody2(){
//		return "<p>[[jCMS:rdMenu{&#39;direction&#39;:&#39;0&#39; }]]</p>\n\n<p>child1</p>\n\n<div class=\"rdsucces\">\n<p>rdsucces</p>\n</div>\n<div class=\"rderror\">\n<p>rderror</p>\n</div>\n\n<div class=\"rdalert\">\n<p>rdalert</p>\n</div>\n\n<div class=\"rdinfo\">\n<p>rdinfo</p>\n</div>\n\n<p>&nbsp;</p>\n";
//	}
}
