<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="models.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
DataManager manager = (DataManager)getServletContext().getAttribute("DataManager");
User curUser = (User) session.getAttribute("user");
if(curUser == null) {
	response.sendRedirect("index.jsp");
}
ArrayList<Message> userMessages = manager.getUserMessages(curUser.getId());
%>
<%!String createMessageBullet(DataManager manager, Message message) {
		String quizLink = "";
		if (message.getType() == Message.CHALLENGE) {
			int quizId = message.getQuizId();
			quizLink = "<a href='quizSummary.jsp?id=" + quizId + "'>" + manager.getQuiz(quizId).getName() + "</a>";
		}
		String bullet = "<li>From: <a class='userlink' href='user.jsp?id=" + message.getSenderId() + "'>" + manager.getUser(message.getSenderId()).getUsername() + "</a><br />" + message.getBody() + "<br/>";
		bullet += quizLink + "</li>";
		return bullet;
	}%>

<%! String createFriendRequestBullet(DataManager manager, Message message) {
	String bullet = "<li><a class='userlink' href='user.jsp?id=" + message.getSenderId() + "'>" + manager.getUser(message.getSenderId()).getUsername() + "</a><br />" + message.getBody() + "<br/>";
	bullet += "<form action='AddFriendServlet' method='post'><input type='hidden' name='messageId' value='" + message.getMessageId() + "'/><input type='submit' name='requeststatus' value='Accept'/></form>";
	bullet +="<form action='AddFriendServlet' method='post'><input type='hidden' name='messageId' value='" + message.getMessageId() + "'/><input type='submit' name='requeststatus' value='Reject'></form>";
	return bullet;
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="messages.css" />
<link rel="stylesheet" type="text/css" href="main.css" />
<script type="text/javascript" src="insertHeader.js"></script>
<title>Messages</title>
</head>

<body>
	<div id="header"></div>
	<br />
	<div>
		<a href="composemessage.jsp">Compose a message...</a>
	</div>
	<div class="title">Messages</div>
	<div>
		<table id="messagebar">
			<tr>
				<td>
					<div class="messagetype">Notes</div>
				</td>
				<td>
					<div class="messagetype">Friend Requests</div>

				</td>
				<td>
					<div class="messagetype">Challenges</div>
				</td>
			</tr>
			<tr>
				<td>
					<ul>
						<%for(Message message : userMessages) {
                	if(message.getType() == Message.REGULAR) {
                		out.println(createMessageBullet(manager, message));
                	}
                }%>
					</ul>
				</td>
				<td>
					<ul>
					<%for(Message message : userMessages) {
	                	if(message.getType() == Message.REQUEST) {
	                		out.println(createFriendRequestBullet(manager, message));
	                	}
	                }
	                %>
					</ul>
				</td>
				<td>
					<ul>
						<%for(Message message : userMessages) {
                	if(message.getType() == Message.CHALLENGE) {
                		out.println(createMessageBullet(manager, message));
                	}
                }
                %>
					</ul>
				</td>
			</tr>
		</table>
	</div>
</body>

</html>