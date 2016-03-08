<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="models.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
User curUser = (User) session.getAttribute("user");
if(curUser == null) {
	response.sendRedirect("index.jsp");
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Compose a Message</title>
<link rel="stylesheet" type="text/css" href="main.css" />
<script type="text/javascript" src="insertHeader.js"></script>
</head>
<body>
<div id="header"></div>
<br />
<form action="MessageServlet" method="post">
  Recipient username:<br>
  <input type="text" name="recipientUsername" ><br/>
  Message Text:<br/>
  <textarea type="text" rows="5" name="message"></textarea><br/>
  <input type="submit" value="Send Message"/>
  <br/>
  <% 
  if(request.getParameter("success") != null) {
	  if(request.getParameter("success").equals("true")) out.println("Message Sent!");
	  else if (request.getParameter("success").equals("false")) out.println("User doesn't exist");
  }
  %>
</form>
</body>
</html>