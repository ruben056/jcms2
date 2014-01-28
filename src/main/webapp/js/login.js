$(function() {

	// don't fully understand why this is required but hey ...
//	$('#captcha script').remove();
	
	$('.tabs').tabs({
//		show:function(event, ui){
//			if($("#captcha", ui.panel).length)
//				return;
//			
//			$("table tr:last", ui.panel).before($("#captcha"));
//		}
		
	});

	var submitForm = function(e){
		
		var email = $("input#email").val();
		var pass = $("input#password").val();
		console.log(email + " " + pass);
		
		if(email != null && email != ""
			&& pass != null && pass != ""){
			
			$.ajax({
				url: "/cms/users/login",
				type:"post",
				data: {
					email : email,
					pass : pass
				}
			}).done(function(data){
				console.log(data);
				data = JSON.parse(data);
				if(data.msgs){
					console.log("messages: \n" + data.msgs);
				}
				if(data.error){
					alert("error occured :\n" + error);
					return;
				}
				document.location.href='/cms/admin';
			}).fail(function(data){
				console.log(data);
				data = JSON.parse(data);
				if(data.msgs){
					console.log("messages: \n" + data.msgs);
				}
				if(data.error){
					alert("error occured :\n" + error);
					return;
				}
			});
		}
		
		//tODO msgs must fill in email and pass!!

		e.preventDefault();
		return false;
	};
	
	$("form").bind({
			submit: submitForm
	});
});


