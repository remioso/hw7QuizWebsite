<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="models.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	User curUser = (User) session.getAttribute("user");
	if(curUser == null) {
		response.sendRedirect("index.jsp");
	}
	ArrayList<User> results = (ArrayList<User>) session.getAttribute("searchresults");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="main.css" />
    <script type="text/javascript" src="insertHeader.js"></script>
<title>Search Results</title>
</head>
	<%!String createBullet(User user) {
		String bullet = "<li><a href = 'user.jsp?id=" + user.getId() + "'>" + user.getUsername() + "</a></li>";
		return bullet;
	}%>
<body>
<div id="header"></div>
<h1>Search Results</h1>
		<%
			if(results != null) {
				for (User user: results)  {
					out.print(createBullet(user));
				}
			} else {
				out.println("No users found.");
			}
		%>
</body>
</html>