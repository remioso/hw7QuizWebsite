<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="models.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Information Incorrect</title>
<link rel="stylesheet" type="text/css" href="main.css" />
</head>
<body>
	<% // This redirects the page if the user is already logged in
		User curUser = (User) session.getAttribute("user");
		if(curUser != null) {
			 response.sendRedirect("homepage.jsp");
		}
	%>
	<h1>Please Try Again</h1>
	<p>Either your user name or password is incorrect. Please try again. </p>
	<form action="LoginServlet" method="post">
	User Name: <input type="text" name = "username" /> <br>
	Password: <input type="text" name = "password" />
	<input type="submit" value="Login"><br>
	</form>
	<br />
	<a href="createaccount.jsp">Create New Account</a>
</body>
</html>