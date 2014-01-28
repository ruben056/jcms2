<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Arrays"%>
<%@page import="com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array"%>
<%@page import="java.util.Date"%>
<%
// 	Object o = session.getAttribute("role");
// 	if(o == null){
// 		response.sendRedirect("login.jsp");
// 	}else{
// 		String[] roles = (String[])o;
// 		if(Arrays.binarySearch(roles, "admin") < 0){
// 			response.sendRedirect("login.jsp");
// 		}
// 	}
%>
<% String path = getServletContext().getContextPath(); %>
<html>
<head>
<script	src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script	src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="<%=path %>/js/jquery.jstree/_lib/jquery.hotkeys.js"></script>
<script src="<%=path %>/js/jquery.jstree/_lib/jquery.cookie.js"></script>
<script src="<%=path %>/js/jquery.jstree/jquery.jstree.js"></script>
<script src="<%=path %>/js/jquery.livequery.js"></script>
<script src="<%=path %>/admin/js/admin.js"></script>
<script src="<%=path %>/admin/js/ckeditor/ckeditor.js"></script>

<link rel="stylesheet" href="<%=path %>/admin/js/ckeditor/contents.css">
<link rel="stylesheet" href="<%=path %>/admin/css/admin.css" />
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
<link rel="stylesheet" href="<%=path %>/js/jquery.jstree/themes/default/style.css"/>
</head>
<body>
	<div id="header">
		<div id="menu-top">
			<ul>
				<li><a href="layout.jsp">Layout</a></li>
				<li><a href="pages.jsp">Pages</a></li>
				<li><a href="users.jsp">Users</a></li>
				<li><a href="plugins.jsp">Plugins</a></li>
				<li><a href="logout.jsp"> Log Out</a></li>
			</ul>
		</div>
	</div>
	<div id="wrapper">
	
	</div>
</body>
</html>