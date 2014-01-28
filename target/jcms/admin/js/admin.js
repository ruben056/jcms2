function Page(){
	this.id = "";
	this.parentID = "";
	this.name = "";	
	this.title = "";
	this.body = "";	
	this.template = "";
	this.type = 0;
	this.keywords = "";
	this.description = "";	
	this.associatedDate = "";
	this.vars = "";
	this.ord=0;
	this.cdate = "";
	this.special = "";
	this.edate = "";
}

function User(){
	this.id 	= "";
	this.email	= "";
	this.pass 	= "";
	this.active = "";
	this.groups = new Array();
	this.activationKey ="";
	this.extras = "";
}

function Group(){
	this.id = "";
	this.name = "";
}


/**
 * ------------------------------------------------------------------------------------------------
 * GENERAL PURPOSE UTILITY
 * ------------------------------------------------------------------------------------------------
 */
function WSUtil(){
	
	var allGroups = undefined;
	
	this.init = function (){
		"use strict";
		initGroups(true);
	};

		
	function initGroups(async) {
		"use strict";
		// get all groups
		WSUtil.getAllGroupsWS(function(data) {
			allGroups = data.obj;
		}, async);
	}

	this.getAllGroups = function(){
		if(!allGroups){
			initGroups(false);
		}
		return allGroups;
	};
}

// extend with static function:
WSUtil.containsGroup = function (id, $arr){
	"use strict";
	for(var i = 0; i < $arr.length; i++){
		if(id == $arr[i]['id'])
			return true;
	}
	return false;
};

/**
 * check returned par, returns false if it contains errors
 * @param data
 */
WSUtil.processResponse = function(data, doneFunction){
	"use strict";
	console.log(data);
	data = JSON.parse(data);
	if(data.msgs){
		console.log("messages: \n" + data.msgs);
	}
	if(data.error){
		alert("error occured :\n" + error);
		return;
	}
	doneFunction(data);
};

/**
 * static function that should be used to save a page.
 *
 * @page contains info about the page + the action (update/insert)
 * @doneFunction is executed after the ajax request completed/returned successfully
 */
WSUtil.savePage = function(page, action, doneFunction) {
	"use strict";
	console.log("saving page :" + page);
	$.ajax({
		type : "POST",
		url : "/cms/admin/pages/controller",
		data : {
			"action" : action,
			"object" : JSON.stringify(page)
		}
	}).done(function(data) {
		WSUtil.processResponse(data, doneFunction);
	});
};

WSUtil.getView = function (name, doneFunction, errFunction){
	"use strict";
	console.log("retrieving view: " + name);
	$.ajax({		
		url		: "/cms/admin/views",
		type	: "POST",
		dataType: 'text',  // TODO this could be an issue... debug ...
		data	: {"name" : name}
		}
	).done(function(data) {
		WSUtil.processResponse(data, doneFunction);
	}).fail(function(e){
		errFunction(e);
	});	
};

WSUtil.getPageByID = function(pageID, doneFunction, errFunction) {
	"use strict";
	$.ajax({
		type : "POST",
		url : "/cms/admin/pages/controller",
		data : {
			action : "getPageById",
			id : pageID
		}
	}).done(function(data) {
		WSUtil.processResponse(data, doneFunction);
	}).fail(function(e){
		errFunction(e);
	});
};

/**
 * Retrieves possible parents for a given page as html options
 * @param pageID
 * @param doneFunction
 * @param errFunction
 */
WSUtil.getPossibleParents = function(pageID, doneFunction, errFunction) {
	"use strict";
	$.ajax({
		type: "post",
		url : "/cms/admin/pages/controller",
		data : {
				"action": "getParents",
				"id": pageID
			},
		cache : false
	}).done(function(data) {
		WSUtil.processResponse(data, doneFunction);
	}).fail(function(e){
		errFunction(e);
	});
};

WSUtil.movePage = function(pageID, parentID, newOrder, doneFunction) {
	"use strict";
	$.ajax({
		url: "/cms/admin/pages/controller",
		type: "post",
		data:{"action":"movePage",
			"id":pageID, 
			"newparent_id":parentID,
			"order": JSON.stringify(newOrder)}
	}).done(function(data) {
		WSUtil.processResponse(data, doneFunction);
	});
};

WSUtil.deletePage = function(pageID, doneFunction) {
	"use strict";
	$.ajax({
		url: "/cms/admin/pages/controller",
		type: "post",
		data:{"action":"DeletePage",
			"id":pageID}
	}).done(function(data) {
		WSUtil.processResponse(data, doneFunction);
	});
};

WSUtil.getAllGroupsWS = function(doneFunction, async) {
	"use strict";
	$.ajax({
		  url: '/cms/admin/users/controller',
		  type: 'POST',
		  async: async,
		  data: {
				action : "GetAllGroups"
			}			  
	}).done(function(data){
		WSUtil.processResponse(data, doneFunction);
	});
};

//TODO should this not be named getAllUsers
WSUtil.getUsersForList = function(doneFunction, errFunction) {
	$.ajax(	{
		url:"/cms/admin/users/controller",
		type: "POST",
		data: {"action":"GetUsersForList"}
	}).done(function(data) {
		WSUtil.processResponse(data, doneFunction);
	}).fail(function(e){
		errFunction(e);
	});
};

WSUtil.deleteUser = function(userID, doneFunction) {
	$.ajax({
		url: "/cms/admin/users/controller",
		type: "POST",
		data: {"action" : "delete", "id" : userID}
	}).done(function(data){
		WSUtil.processResponse(data, doneFunction);
	});	
};

WSUtil.getUserByID = function(userID, doneFunction) {
	$.ajax({
		url: "/cms/admin/users/controller",
		type: "POST",
		data: {"action": "GetByID", "id": userID}
	}).done(function(data){
		WSUtil.processResponse(data, doneFunction);
	});
};

WSUtil.saveUser = function(usr, doneFunction) {
	var s = JSON.stringify(usr);
	$.ajax({
		type: "POST",
		url: "/cms/admin/users/controller",
		data: {"action": "Save",
			"object": s
		}
	}).done(function(data){
		WSUtil.processResponse(data, doneFunction);
	});
};

WSUtil.getPlugins = function(doneFunction, errFunction) {
	$.ajax(	{
		url:"/cms/admin/plugins/controller",
		type: "POST",
		data: {"action":"GetPlugins"}
	}).done(function(data) {
		WSUtil.processResponse(data, doneFunction);
	}).fail(function(e){
		alert(e);
	});
};

WSUtil.disablePlugin = function(doneFunction, name){		
	$.ajax(	{
		url:"/cms/admin/plugins/controller",
		type: "POST",
		data: {"action":"disablePlugin", "name":name}
	}).done(function(data) {
		WSUtil.processResponse(data, doneFunction);
	}).fail(function(e){
		alert(e);
	});
};

WSUtil.retrieveAllLayouts = function(doneFunction){		
	$.ajax(	{
		url:"/cms/admin/layout/controller",
		type: "POST",
		data: {"action":"retrieveAll"}
	}).done(function(data) {
		WSUtil.processResponse(data, doneFunction);
	}).fail(function(e){
		alert(e);
	});
};

WSUtil.enableLayout = function(doneFunction, layoutID){		
	$.ajax(	{
		url:"/cms/admin/layout/controller",
		type: "POST",
		data: {"action":"enableLayout", "id":layoutID}
	}).done(function(data) {
		WSUtil.processResponse(data, doneFunction);
	}).fail(function(e){
		alert(e);
	});
};

//WSUtil.saveLayout = function(doneFunction, layout){
//	var s = JSON.stringify(layout);
//	$.ajax(	{
//		url:"/cms/admin/layout/controller",
//		type: "POST",
//		data: {"action":"save", "obj": s}
//	}).done(function(data) {
//		WSUtil.processResponse(data, doneFunction);
//	}).fail(function(e){
//		alert(e);
//	});
//};

/**
 * Initialization of some global variables
 */
var wsUtil = new WSUtil();
var pageMgmt;
var userMgmt;
var pluginMgmt;

/**
 * ------------------------------------------------------------------------------------------------
 * ONLOAD FUNTIONALITY
 * ------------------------------------------------------------------------------------------------
 */
$(function() {	
	wsUtil.init();
	$('.date-human').livequery(function(){
		$(this).datepicker();
	});
	var $wrapper = $('div#wrapper');
	$wrapper.empty();
	

	var $pagesLink = $('div#menu-top li a[href="pages.jsp"]');
	$pagesLink.click(function(e){
		$wrapper.empty();
		$('div#jstree-marker').remove();
		$('div#jstree-marker-line').remove();
//		$('div#vakata-contextmenu').remove();
		$('div#ui-datepicker-div').remove();
		
		var $leftMenu = $('<div class="left-menu">');
		var $hasLeftMenu = $('<div class="has-left-menu">');
		$wrapper.append($('<h1>Pages</h1>'));
		$wrapper.append($leftMenu);
		$wrapper.append($hasLeftMenu);
		
		pageMgmt = new PageMgmt($leftMenu, $hasLeftMenu);
		pageMgmt.initPages();
		
		e.preventDefault();
		return false;
	});
	
	var $pagesLink = $('div#menu-top li a[href="layout.jsp"]');
	$pagesLink.click(function(e){
		$wrapper.empty();
		$('div#jstree-marker').remove();
		$('div#jstree-marker-line').remove();
//		$('div#vakata-contextmenu').remove();
		$('div#ui-datepicker-div').remove();
		
		var $leftMenu = $('<div class="left-menu">');
		var $hasLeftMenu = $('<div class="has-left-menu">');
		$wrapper.append($('<h1>Layout</h1>'));
		$wrapper.append($leftMenu);
		$wrapper.append($hasLeftMenu);
		
		layoutMgmt = new LayoutMgmt($leftMenu, $hasLeftMenu);
		layoutMgmt.initLayout();
		
		e.preventDefault();
		return false;
	});
	
	var $usersLink = $('div#menu-top li a[href="users.jsp"]');
	$usersLink.click(function(e){
		
		$wrapper.empty();
		$('div#jstree-marker').remove();
		$('div#jstree-marker-line').remove();
//		$('div#vakata-contextmenu').remove();
		$('div#ui-datepicker-div').remove();
		
		var $leftMenu = $('<div class="left-menu">');
		var $hasLeftMenu = $('<div class="has-left-menu">');
		
		$wrapper.append($('<h1>User Management</h1>'));
		$wrapper.append($leftMenu);
		$wrapper.append($hasLeftMenu);
		
		userMgmt = new UserMgmt($leftMenu, $hasLeftMenu);
		userMgmt.initUsers($leftMenu, $hasLeftMenu);
		
		e.preventDefault();
		return false;
	});
	
	var $pluginLink = $('div#menu-top li a[href="plugins.jsp"]');
	$pluginLink.click(function(e){
		$wrapper.empty();
		$('div#jstree-marker').remove();
		$('div#jstree-marker-line').remove();
//		$('div#vakata-contextmenu').remove();
		$('div#ui-datepicker-div').remove();
				
		var $leftMenu = $('<div class="left-menu">');
		var $hasLeftMenu = $('<div class="has-left-menu">');
		
		$wrapper.append($('<h1>Pugins</h1>'));
		$wrapper.append($leftMenu);
		$wrapper.append($hasLeftMenu);
		
		pluginMgmt = new PluginMgmt($leftMenu, $hasLeftMenu);
		pluginMgmt.initPlugins($leftMenu, $hasLeftMenu);
		
		e.preventDefault();
		return false;
	});
	
	var $logoutLink = $('div#menu-top li a[href="logout.jsp"]');
	$logoutLink.click(function(e){
		
		$wrapper.empty();
		$('div#jstree-marker').remove();
		$('div#jstree-marker-line').remove();
//		$('div#vakata-contextmenu').remove();
		$('div#ui-datepicker-div').remove();
		
		$wrapper.append($('<h1>Logout</h1>'));
		$.ajax({
			type: "post",
			url: "/cms/users/logout"
		}).done(function(e){
			$wrapper.append("<h2> logged out </h2>");
			document.location.href="/cms/admin";
		});
		e.preventDefault();
		return false;
	});
});


/**
 * ------------------------------------------------------------------------------------------------
 * ALL FUNCTIONS FOR LAYOUT MANAGEMENT
 * ------------------------------------------------------------------------------------------------
 */

function LayoutMgmt($leftMenu, $hasLeftMenu){
	"use strict";
	var $left = $leftMenu;
	var $right = $hasLeftMenu;
	
	this.initLayout = function (){
		"use strict";
		var that = this;
		var $a = $('<a href="#">Layout</a>');
		$left.append($a);
		$a.click(function(e){
			$right.empty();
			that.buildLayoutInfo();
			
			e.preventDefault();
			return false;
		});
		WSUtil.retrieveAllLayouts(function(data){
			$right.append($('<h2>Layout Management</h2>'));
			that.buildLayoutInfo(data);
		});
		
	};
	
	this.buildLayoutInfo = function(data){
		"use strict";
		var that = this;
			
		var $table = $('<table>');			
		var $row1 = $('<tr><th>Layout Name</th><th>Active</th></tr>');
		$table.append($row1);
		var layouts = data.obj;
		for(var i =0; i < layouts.length; i++){
			var cur = layouts[i];
			var name = cur["name"];
			var active = cur["enabled"];
			var msg = '<tr>';
			msg += '<td>' + name + '</td>';		
			if(active){
				msg += '<td>enabled</td>';
			}else{
				msg += '<td><a href="#" args='+ cur["id"] +'>' + "enable" + '</a></td>'; // when clicking
			}
			 				
			msg +=	'</tr>';
	 				var $r = $(msg);
			$table.append($r);
		}
		var $oldTable = jQuery("table", $right);
		if($oldTable.length > 0){
			$oldTable.replaceWith($table);
		}else{
			$right.append($table);
		}
					
		$table.find("a[href=#]").click(function(e){
			var layoutID = jQuery(this).attr("args");
			WSUtil.enableLayout(function(data){
				that.buildLayoutInfo(data); // refresh
			}, layoutID);
		});
	};
}
/**
 * ------------------------------------------------------------------------------------------------
 * ALL FUNCTIONS FOR PLUGIN MANAGEMENT
 * ------------------------------------------------------------------------------------------------
 */
function PluginMgmt($leftMenu, $hasLeftMenu){
	"use strict";
	var $left = $leftMenu;
	var $right = $hasLeftMenu;
	
	this.initPlugins = function (){
		"use strict";
		var that = this;
		var $a = $('<a href="#">Plugins</a>');
		$left.append($a);
		$a.click(function(e){
			$right.empty();
			that.buildPluginList();
			
			e.preventDefault();
			return false;
		});
		that.buildPluginList();
	};
	
	this.buildPluginList = function(){
		//TODO retrieve the plugins and show them in a table somilar to the users
		// allow to enable disable them that is about it...
		WSUtil.getPlugins(function(data){
			$hasLeftMenu.append($('<h2>Plugin Management</h2>'));
			var $table = $('<table>');
			$right.append($table);		
			var $row1 = $('<tr><th>Plugin name</th><th>Description</th><th>Active</th></tr>');
			$table.append($row1);
			var plugins = data.obj;
			for(var i =0; i < plugins.length; i++){
				var plugin = plugins[i];
				var name = plugin["name"];
				var descr = plugin["description"];
				var active = plugin["enabled"];
				var msg = '<tr>';
				msg += '<td>' + name + '</td>';
				msg += '<td>' + descr + '</td>';				
				msg += '<td><a href="#" args='+ name +'>' + ((active)?"disable":"enable") + '</a></td>'; // when clicking 				
				msg +=	'</tr>';
	 
				var $r = $(msg);
				$table.append($r);
			}
			
			$table.find("a").click(function(e){
				var name = $(this).attr("args");
				//TODO
				WSUtil.disablePlugin(name);
				
				e.preventDefault();
				return false;
			});
			
		}, function(e){
			alert(e);
		});
	};
}

/**
 * ------------------------------------------------------------------------------------------------
 * ALL FUNCTIONS FOR USER MANAGEMENT
 * ------------------------------------------------------------------------------------------------
 */
	
function UserMgmt($leftMenu, $hasLeftMenu){
	"use strict";
	var $left = $leftMenu;
	var $right = $hasLeftMenu;

	this.initUsers = function (){
		"use strict";
		var that = this;
		var $a = $('<a href="#">Users</a>');
		$left.append($a);
		$a.click(function(e){
			$right.empty();
			that.buildUserList($right);
			
			e.preventDefault();
			return false;
		});
		that.buildUserList($right);
	};
	
	this.buildUserList = function (){
		"use strict";
		var that = this;		
		WSUtil.getUsersForList(function(data) {
			$hasLeftMenu.append($('<h2>User Management</h2>'));
			var $table = $('<table>');
			$right.append($table);		
			var $row1 = $('<tr><th>User</th><th>Groups</th><th>Actions</th></tr>');
			$table.append($row1);
			var users = data.obj;
			for(var i =0; i < users.length; i++){
				var user = users[i];
				var id = user['id'];
				var email = user['email'];
				var groups = new Array();
				var parseGrps = user['groups'];
				if(parseGrps != null){
					groups = new Array(parseGrps.length);
					for(var j=0; j < parseGrps.length; j++){
						groups[j] = parseGrps[j]["name"];
					}
				}				
				
				var msg = '<tr><td>';
				msg += '<a href="users.jsp?id='+ id + '" args="' + id + '">' + email + '</a>';
				msg +='</td>';
				msg += '<td>';
				for(var j=0; j< groups.length; j++){
					if(j != 0)
						msg+=",";
					msg += groups[j];
				}
				msg += '</td>';
				msg += '<td>';
				msg += '<a href="users.jsp?id='+ id + '" args='+id+'>edit</a>';
				msg += '&nbsp;<a class="lnkDelete"';
				msg +=		'href="users.jsp?id='+ id 
							+'&amp;action=delete" args='+id+'>[x]';
				msg +=	'</a></td></tr>';
	 
				var $r = $(msg);
				$table.append($r);
			}
			var $newUser = $('<a class="button" href="users.jsp?id=-1">Create User</a>');
			$right.append($newUser);
			
			$newUser.click(function(e){
				that.showEditForm(-1);
				e.preventDefault();
				return false;
			});

			$table.find("a").click(function(e){
				var id = $(this).attr("args");
				if($(this).hasClass("lnkDelete")){
					if(!confirm("Are you sure you want to delete this user?")){
						event.preventDefault();
						return false;
					}
					WSUtil.deleteUser(id, function(data){
						that.refreshUserList();
					});
				}else{
					that.showEditForm(id);
				}
				e.preventDefault();
				return false;
			});
			
		},function(e){
			alert(e);
		});		
	};
	
	this.refreshUserList = function(){
		"use strict";
		$right.empty();
		this.buildUserList();
	};
	
	this.showEditForm = function(id){
		"use strict";
		var that = this;
		var userInfo = new User();
		if(id === undefined || id <= 0){
			userInfo.id = 0;
			userInfo.email = "";
			userInfo.groups = new Array();
			userInfo.active = false;
			that.openUserDialog(userInfo);
		}else{
			WSUtil.getUserByID(id, function(result){
				that.openUserDialog(result.obj);
			});
		}
	};
	
	this.openUserDialog = function(userInfo){
		"use strict";
		var that = this;
		var id = userInfo["id"];
		var email = userInfo["email"];
		var active= userInfo["active"];
		var curGrps = userInfo["groups"];
		var s = '<form action="users.jsp?id='+ id + '" method="post">' +
		'<input type="hidden" id="id" name="id" value="'+id+'" /><table>' +
		'<tr><th>Email</th><td><input id="email" name="email" value="' + email + '" /></td>' +
		  '</tr>' +
		 '<tr><th>Password</th>' +
		   '<td><input id="password" name="password" type="password" /></td>' +
		 '</tr>' +
		'<tr><th>(repeat)</th><td><input id="password2" name="password2" type="password" /></td></tr>'+
		'<tr>'+
		 '<th>Groups</th>'+
		  '<td class="groups">';
		var allGrps = wsUtil.getAllGroups();
		for(var i = 0; i < allGrps.length ; i++){
			var cur = allGrps[i];
			s+='<input type="checkbox" class="userGroups" name="' + cur['name'] + '" id="' + cur['id'] + '"';
			if(WSUtil.containsGroup(cur['id'], curGrps)){
				s+='checked="checked" ';
			}
			s+=' />';
			s+= cur["name"] + " <br/>";
		}
		s+='</td></tr>';

		// }
		s+= '<tr><th>Active</th><td><select name="active">' +
				'<option value="0" ';
		if(active=="0"){
			s+='selected="selected"';
		}
		s+='>No</option><option value="1" ';
		if(active=="1"){
			s+= 'selected="selected"';
		} 
		s+='>Yes</option>'+
		'</select></td>' + 
		'</tr>';
		s+= '</table>';
		s+= '</form>';
			
		$(s).dialog({
			modal:true,
			buttons:{
					'Save': function() {
						var id = $(this).find("input#id").val();
						var $allGrps = $("input.userGroups", "td.groups");
						var groups = new Array();
						$allGrps.each(function(){
							if($(this).prop("checked")){
								var cbo = $(this);
								var tmp = new Group();
								tmp.id = cbo.attr("id");
								tmp.name = cbo.attr("name");
								groups.push(tmp);
							}
						});
						var isactive = 0;
						var $active = $("option","select[name='active']");
						$active.each(function(){
							if($(this).prop("selected")){
								isactive = $(this).val();
							}
						});
						var email = $(this).find("input#email").val();
						var pass = $(this).find("input#password").val();
//						var pass2 = $(this).find("input#password2").val();
						
						//TODO compare pass1 en pass2 and show message if not similar!!
						
						var usr = new User();
						usr.id = id;
						usr.groups  = groups;
						usr.email = email;
						usr.active = isactive == "1" ? true: false;
						usr.pass = pass;
						
						WSUtil.saveUser(usr, function(data){
							that.refreshUserList();
						});
						$(this).dialog('destroy');
						$(this).remove();
					},
					'Cancel': function() {
						$(this).dialog('destroy');
						$(this).remove();
					}
			}
		});
	};
}


/**
 * ------------------------------------------------------------------------------------------------
 * ALL FUNCTIONS FOR PAGE MANIPULATION:
 * ------------------------------------------------------------------------------------------------
 */
function PageMgmt($leftMenu, $hasLeftMenu) {
	"use strict";
	var that = this;
	var currentpageid = 0;
	
	this.initPages = function() {
		"use strict";
		this.initForm();
		this.initMenu(-1);
	};

	this.initForm = function() {
		"use strict";
		var that = this;
		WSUtil.getView("admin/views/pageForm.html", function(data) {
			var htmlForm = data.obj;
			$hasLeftMenu.html(htmlForm);			
			var $pageLink = $("#tabs-common-details a#viewPage");
			$pageLink.hide();
			$(".tabs").tabs();
			
			that.fillParentPagesList(currentpageid);
			that.formAction();
			var editor = CKEDITOR.replace( 'body',
				    {
				        filebrowserBrowseUrl : '/cms/admin/browser/browse',
//						filebrowserBrowseUrl : '/cms/admin/browser/browser.jsp?dir=/usr/share/tomcat7/webapps/cmsUploadFolder',
				        filebrowserUploadUrl : '/cms/admin/uploader/upload',
				        filebrowserWindowWidth : '320',
				        filebrowserWindowHeight : '240'
				    });
			
			editor.on("instanceReady",function() {
				editor.addCommand( "save", {
				    modes : { wysiwyg:1, source:1 },
				    exec : that.savePage
				 });
			});
				
			}, function(e) {
			alert(e);
		});
	};

	this.initMenu = function(nodeID) {
		"use strict";
		var that = this;

		WSUtil.getView("admin/views/treeWrapper.html", function(data) {
			var htmlMenu = data.obj;
			$leftMenu.html(htmlMenu);
			that.initJSTree(nodeID);
		}, function(e) {
			alert(e.statusText);
		});
	};
	
	this.formAction = function(){
		"use strict";
		var that=this;
		var $submit = $("input#submitForm");
		$submit.click(that.savePage);
	};
	
	this.savePage = function(e){
		
		// retrieve current parent:
		var $option = $("#pages_form select[name=parent]").find("option");
		var $selected;
		$option.each(function(){
			if($(this).prop("selected")){
				$selected = $(this);
			}
		});
		var parent = $selected.attr("value");;
		var oldParent = $("#pages_form select[name=parent]").find("option[selected='selected']").attr("value");
		var action = $("input#submitForm").val();
		var id = $("#tabs-common-details input#id").val();
		var oldName = $("#tabs-common-details input#name").attr("value");
		var newName = $("#tabs-common-details input#name").val();
		var special = 0;
		var $home = $("#tabs-advanced-options input[name='special\\[0\\]']");
		if($home.prop("checked")){
			special = 1; //"Home"
		}
		var $hidden = $("#tabs-advanced-options input[name='special\\[1\\]']");
		if($hidden.prop("checked")){
			special += 2; //"isHidden"
		}
		var $options = $("#tabs-advanced-options select[name='page_vars[order_of_sub_pages]']").children();
		var $options2 = $("#tabs-advanced-options select[name='page_vars[order_of_sub_pages_dir]']").children();

		
		var pageInfo = new Page();			
		pageInfo.id = (id=="" || id=="undefined" || id < 0) ? 0 : id;
		pageInfo.type = 0;
		pageInfo.parentID = parent;
		pageInfo.keywords = $("#tabs-advanced-options input#keywords").val();
		pageInfo.description = $("#tabs-advanced-options input#description").val();
		pageInfo.associatedDate = $("#tabs-common-details input#associated_date").val();
		pageInfo.title 	= $("#tabs-common-details input#title").val();
		pageInfo.body 	= CKEDITOR.instances.body.getData();
		pageInfo.name 	= newName;
		pageInfo.special = special;
		
		//TODO Page must be extended for these to fields
		$options.each(function(){
			if($(this).prop("selected")){
				console.log("options : " + $(this).text() + " is selected");
			}
		});
		$options2.each(function(){
			if($(this).prop("selected")){
				console.log("sub options : " + $(this).text() + " is selected");
			}
		});	
		WSUtil.savePage(pageInfo, action, function(data){
			var result = data.obj;
			if(id <= 0 || id == ""){
				id = result["id"];
			}
			if(oldParent != parent || oldName != newName){
				var $leftMenu = $('div.left-menu');
				$leftMenu.empty();
				that.initMenu(id);
			}
		});
		e.preventDefault();
		return false;
	};
	
		/**
	 * Ajax function to fill form values,
	 * triggered from menu.js when a leaf is clicked in the jstree
	 */
	this.fillFormValues = function(pageID){
		"use strict";
		var that = this;
		       
		if(pageID){
//			var query = encodeURIComponent("SELECT * FROM pages WHERE id="+pageID);
			WSUtil.getPageByID(pageID
				, function(data) {
					// decode the jsonData
					// COMMON TAB
					var $id = $("#tabs-common-details input#id");
					var $name = $("#tabs-common-details input#name");
					var $title = $("#tabs-common-details input#title");
					var $pageLink = $("#tabs-common-details a#viewPage");
					var $pageType = $("#tabs-common-details input#type");
					var $aDate = $("#tabs-common-details input#associated_date");
						
					var $kw = $("#tabs-advanced-options input#keywords");
					var $desc = $("#tabs-advanced-options input#description");
					var $isHome = $("#tabs-advanced-options input[name='special[0]']");
					var $isHidden = $("#tabs-advanced-options input[name='special[1]']");
					var $order = $("#tabs-advanced-options select#order");
					var pageData = data.obj; 
					if(pageData){
						// TODO page type
						$id.val(pageData.id);
						$name.val(pageData.name);
						$title.val(pageData.title);
						$pageLink.attr("href", "/cms/public?id="+pageData.id);
						$pageLink.show();
						$aDate.val(pageData.associatedDate);
						that.fillParentPagesList(pageID);
						CKEDITOR.instances.body.setData(pageData.body);
						
						// ADVANCED TAB
						$kw.val(pageData.keywords);
						$desc.val(pageData.description);
						if((pageData.special&1)){
							$isHome.prop("checked", true);
						}else{
							$isHome.prop("checked", false);
						}
						if((pageData.special&2)){
							$isHidden.prop("checked", true);
						}else{
							$isHidden.prop("checked", false);
						}
						
						var $options = $("select[name='page_vars[order_of_sub_pages]']").children();
						$options.each(function(){
							if($(this).prop("selected")){
								console.log("options : " + $(this).text() + " is selected");
							}
						});
							
						var $options2 = $("select[name='page_vars[order_of_sub_pages_dir]']").children();
						$options2.each(function(){
							if($(this).prop("selected")){
								console.log("sub options : " + $(this).text() + " is selected");
							}
						});
						$("#submitForm").attr("value", "Update Page Details");
					}else{
		
						$id.val("");
						$name.val("");
						$title.val("");
						$pageLink.hide();
						$aDate.val("");
						that.fillParentPagesList(0);
						
						// ADVANCED TAB
						$kw.val("");
						$desc.val("");
						$isHome.prop("checked", false);
						$isHidden.prop("checked", false);
						
						var $options = $order.find("option");
						$options.each(function(){
							$(this).removeAttr("selected");
						});
						
						$pageLink.hide();
						$("input#submitForm").attr("value", "Insert Page Details");
					}
				}	
				, function(e) {
					alert(e.statusText);
				});
		}
	};

	/**
	 * Retrieves possible parents of the curent page ands at them to the
	 * dropdown list
	 */
	this.fillParentPagesList = function(id) {
		"use strict";
		$("#pages_form select[name=parent]").children().remove().end();
		WSUtil.getPossibleParents(id
			, function(result) {
				$("#pages_form select[name=parent]").html(result.obj);
			}
			, function(e) {
				alert(e.statusText);
			}
		);
	};

	this.initJSTree = function(nodeID) {
	
		if(nodeID <= 0){
			nodeID = -1;
		}
		
		$('#pages-wrapper-tree').jstree({     
		"json_data" : {
			"ajax" : {
				"url" : "/cms/admin/pages/controller",
				"type": "POST",				
				// result of this function is send in the post of the ajax msg
				"data": function(n){
					return { 
						"id" : n.attr ? n.attr("id") : 0,
						"action" : "retrieveNode"
					};
				}
				,success : function(data){
					"use strict";
					if(data.msgs){
						console.log("messages: \n" + data.msgs);
					}
					if(data.error){
						alert("error occured :\n" + error);
						return;
					}
					return data.obj;
				},
				error : function(data){
					alert("error");
					console.log("error : " +  data);
				}
			
			}
		},
		"plugins" : [ "themes",   // enables the images and styles
		              "json_data", // enables dynamic loading of tree with json data
		              "ui", 
		              "crrm",
		              "hotkeys", 
		              "dnd", // enables drag n drop
	//	              "search",
		              "contextmenu"
		              ],
		"core" : { "initially_open" : [ "phtml_1" ] },
		"ui":{"initially_select" : [ nodeID ]},  //TODO this must somehow become an argument!!!
		"contextmenu" : {
			"items" : {
				'rename' : false
			}
		}
		}).bind("select_node.jstree", function (event, data) {
		    // `data.rslt.obj` is the jquery extended node that was clicked
			var id = data.rslt.obj.attr("id");
			that.fillFormValues(id);
		}).bind("create.jstree", function (event, data) {
			/**This code is entered when using rightclick > create*/
		    // `data.rslt.obj` is the jquery extended node that was clicked
			var parentID = data.rslt.parent.attr("id");
			var name = data.rslt.name;
			
			var pageInfo = new Page();
			pageInfo.id = 0;
			pageInfo.type = 0;
			pageInfo.parentID = parentID;
			pageInfo.name = name;
			pageInfo.title = name;
			pageInfo.special = 2;  // hidden by default

			WSUtil.savePage(pageInfo, "Insert Page Details", function(data){
				var result = data.obj;
				var id = result["id"];
				that.initMenu(id);
			});
			
		}).bind("remove.jstree", function (event, data) {
		    // `data.rslt.obj` is the jquery extended node that was clicked
			var id = data.rslt.obj.attr("id");
			var parentID = data.rslt.np.attr("id");
			WSUtil.deletePage(id, function(data){
				// show parent information since current is deleted
				that.fillFormValues(parentID);
			});
		}).bind("move_node.jstree", function (event, data){
			// called when you drop a page in another parent
			var pageID =data.rslt.o.attr("id");
			var parentID =data.rslt.np.attr("id");
			
			// establish the order of the leafs after the new is added
			// in case a page is moved to the upper level make sure
			// the childnodes are correct!!
			var nodes = $(data.rslt.np[0].children).find(".jstree-leaf");
			if(parentID=="pages-wrapper"){
				nodes = nodes[0].childNodes;
			}
			var newOrder = [];
			for(var i=0; i< nodes.length ; i++){
				newOrder[i] = nodes[i].id;
			}
			WSUtil.movePage(pageID, parentID, newOrder, function() {
				that.fillFormValues(objID);
			});
			
		});
		var $div=$('#pages-wrapper-btns');
		$div.append($('<i>right-click for options</i>'));
		$div.append($('<br/>'));$div.append($('<br/>'));
		$div.append($('<button>add main page</button>').click(that.pages_add_main_page));
		
		// these divs are added to the body and are adding some unwanted css styles as well so just remove them		
		$('div#jstree-marker').remove();
		$('div#jstree-marker-line').remove();
//		$('div#vakata-contextmenu').remove();
		$('div#ui-datepicker-div').remove();
	};
	
	this.pages_add_sub_page = function(node, tree){
		var p = node[0].id.replace('/.*_/', '');
		that.pages_new(p);
	};
	
	this.pages_add_main_page = function(){
		that.pages_new(0);	
	};
	
	// show form dialog box to add a new page with some basic info:
	this.pages_new = function(p){
		$('<form id="newpage_dialog" action="/cms/admin/pages.jsp" method="post">'
		+ '<table>'
		+ '<tr><th>Name</th><td><input id="name" name="name" value=""/></td></tr>'
		+ '<tr><th>Page Type</th><td><select name="type">'
		+ 	'<option value="0">normal</option>'
		+ '</select></td></tr>'
		+ '<tr>'
		+	'<th>Associated Date</th>'
		+ 	'<td><input id="associated_date" name="associated_date" class="date-human"id="newpage_date" value=""/></td>'
		+  '</tr>'
		+ '</table>'
		+ '</form>').dialog({
			modal:true,
			buttons:{
					'Create Page': function() {
						var $date = $(this).find("input#associated_date");
						var $name = $(this).find("input#name");
						var pageInfo = new Page();
						pageInfo.id = 0;
						pageInfo.type = 0;
						pageInfo.parentID = p;
						pageInfo.associatedDate = $date.val();
						pageInfo.name = $name.val();
						pageInfo.special = 0;
						WSUtil.savePage(pageInfo, "Insert Page Details", function(data){
							var result = data.obj;
							var id = result["id"];
							that.initMenu(id);
						});
						$(this).dialog('destroy');
						$(this).remove();
					},
					'Cancel': function() {
						$(this).dialog('destroy');
						$(this).remove();
					}
			}
		});
		return false;
	};
	
	this.getCurrentPageId = function(){
		return currentpageid;
	};
}