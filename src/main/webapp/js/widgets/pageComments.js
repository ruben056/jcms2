function Comment(){
	this.id = "";
	this.text = "";
	this.authorName = "";
	this.authorEmail = "";
	this.authorWebsite = "";
	this.page = {id : ""},
	this.status = "";
	this.cDate = "";
}

jQuery(document).ready(function() {
	'use strict';
	
	refreshComments();
	
});

/**
 * retrieve all comments and show them
 */
function refreshComments(){
	'use strict';
	WSUtil.getView("views/pageComments.html", function(data){
		var $pcs = jQuery("#pageCommentsWrapper");
		if($pcs){
			$pcs.remove();
		}
		var $htmlForm = jQuery(data.obj);
		// add data and then add to the html DOM
		
		jQuery.ajax(	{
			url:"/cms/pagecomment/controller",
			type: "POST",
			data: {"action":"getCommentForPage", "pageID":WSUtil.getCurrentID()}
		}).done(function(data) {
			WSUtil.processResponse(data, function(data){
				var $content = jQuery("#pageCommentsContent", $htmlForm);
				var comments = data.obj;
				for ( var int = 0; int < comments.length; int++) {
					var comment = comments[int];
					var text = comment["text"]; 
					var author = comment["authorName"]; 
					var website = comment["authorWebsite"]; 
					var msg = "<div class='comment'> <p>" + author  + 
						"(<a href='http://"+website+"'>" + website +"</a>) said:" + "</p>" +
						"<p>"+ text + "</p></div>";
					$content.append(jQuery(msg));
				}
				jQuery("div#pageWrapper").append($htmlForm);
				// code for adding comment
				jQuery("#pageCommentsFrm").bind("submit", function(e) {
					try{

						console.log("submitting comment");
						// retrieve and encapsulate info in object
						var c = new Comment();
						var $theForm = jQuery("form#pageCommentsFrm");
						c.authorName = jQuery("input[name='authorName']", $theForm).val();
						c.authorEmail = jQuery("input[name='authorEmail']", $theForm).val();
						c.authorWebsite = jQuery("input[name='authorWebsite']", $theForm).val();
						c.page.id = WSUtil.getCurrentID();
						c.status=1;
						c.id=0;
						c.text = jQuery("textarea[name='text']", $theForm).val();
						
						jQuery.ajax({
							type : "POST",
							url : "/cms/pagecomment/controller",
							data : {
								"action" : "saveComment",
								"object" : JSON.stringify(c)
							}
						}).done(function(data) {
							WSUtil.processResponse(data, function(d){
								refreshComments();
							});
						}).fail(function(e){
							console.log(e);
						});
					}finally{						
					}		
					e.preventDefault();
					return false;
				});
			});
		}).fail(function(e){
			alert(e);
		});
	});
}

/*Refactor this to one java utility class (now is in admin.js but is not included in public part of website */
var WSUtil = function(){};
WSUtil.getView = function(name, doneFunction, errFunction){
	"use strict";
	console.log("retrieving view: " + name);
	jQuery.ajax({		
		url		: "/cms/views",
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
WSUtil.processResponse = function(data, doneFunction){
	"use strict";
	console.log(data);
	data = jQuery.parseJSON(data);
	if(data.msgs){
		console.log("messages: \n" + data.msgs);
	}
	if(data.error){
		alert("error occured :\n" + data.error);
		return;
	}
	doneFunction(data);
};

WSUtil.getCurrentID = function(){
	return jQuery("div#pageWrapper").attr("pageID");
};