<%@page import="rd.mgr.user.User"%>
<%@page import="rd.util.GeneralUtil"%>
<%@page import="rd.util.ComponentFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String path =  getServletContext().getContextPath();%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script	src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script	src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="<%=path %>/js/login.js"></script>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
<link rel="stylesheet" href="<%=path %>/css/login.css" />
<title>Administrator Login</title>
</head>
<body>
<div id="header"></div>
<h1>Login</h1>
<div class="tabs">
		<ul>
			<li><a href="#tab1">Login</a>
			</li>
			<li><a href="#tab2">Forgotten Password</a>
			</li>
		</ul>
		<div id="tab1">
			<form method="post" action="/cms/users/login">
				<table>
					<tr>
						<th>email</th>
						<td><input id="email" name="email" type="email" /></td>
					</tr>
					<tr>
						<th>password</th>
						<td><input type="password" id="password" name="password" /></td>
					</tr>
<!-- 					<tr id="captcha"> -->
<!-- 						<th>captcha</th> -->
<!-- 						<td> 

-->
						
<!-- 						</td> -->
<!-- 					</tr> -->
					<tr>
						<th colspan="2" align="right"><input name="action" type="submit"
							value="login" class="login" /></th>
					</tr>
				</table>
			</form>
		</div>
		<div id="tab2">
			<form method="post" action="TODO">
				<table>
					<tr>
						<th>email</th>
						<td><input id="email" type="text" name="email" /></td>
					</tr>
					<tr>
						<th colspan="2" align="right"><input name="action" type="submit"
							value="resend my password" class="login" /></th>
					</tr>
				</table>
			</form>
		</div>
	</div>	
</body>
</html>