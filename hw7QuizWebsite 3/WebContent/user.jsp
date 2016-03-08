<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.*"%>
<%@ page import="models.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% DataManager manager = (DataManager) getServletContext().getAttribute("DataManager");
	User curUser = (User) session.getAttribute("user");
	if(curUser == null) {
		response.sendRedirect("index.jsp");
	}
	User user = manager.getUser(Integer.parseInt(request.getParameter("id")));
	if(curUser.getId() == (int) Integer.parseInt(request.getParameter("id"))) {
		response.sendRedirect("account.jsp");
	}
	ArrayList<Integer> friendsList = manager.getFriends(user.getId());
	ArrayList<QuizStatistic> statistics = manager.getAllUserQuizStats(user.getId());
	String friendServlet;
	String buttonText;
	if(!manager.isFriend(curUser.getId(), user.getId())) {
		friendServlet = "MessageServlet";
		buttonText = "Add Friend";
	} else {
		friendServlet = "RemoveFriendServlet";
		buttonText = "Remove Friend";
	}
	%>
	<%!String createFriendBullet(User user) {
		String bullet = "<li><a href = 'user.jsp?id=" + user.getId() + "'>" + user.getUsername() + "</a></li>";
		return bullet;
	}%>
	<%!String createQuizStatBullet(DataManager manager, QuizStatistic stat) {
		String bullet = "<li><a href = 'quiz.jsp?id=" + stat.getQuizId() + "'>" + manager.getQuiz(stat.getQuizId()).getName() + "</a>";
		bullet += "<br/>Date: " + stat.getDate().toString() + " Score: " + stat.getScore() + "</li>";
		return bullet;
	}%>


<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="main.css" />
    <script type="text/javascript" src="insertHeader.js"></script>
<title><%=user.getUsername() %>'s Profile</title>
</head>
<body>

    <div id="header"></div>
    
    <h1><%=user.getUsername()%></h1>
	<form action='<%=friendServlet %>' method='post'>
		<input type="hidden" value="<%= user.getId() %>"  name="requestedFriendId"/>
		<input type="hidden" value="<%= user.getId() %>"  name="friendRequest"/>
		<input class='submit' type='submit' value='<%=buttonText %>' />
	</form>
	<br/>
    <div><b>Friends</b><br/>
    <% if (!friendsList.isEmpty()) {
		    for(Integer friendID: friendsList) {
		    	out.println(createFriendBullet(manager.getUser(friendID)));
		    }
    	} else {
    		out.println("No friends :(");
    	}
    	%>
    </div>
    <br/>
        <div>
    <b>History</b>
 	<br/>
 	<%if(statistics == null) {
    	out.println("No quizzes taken");
    } else {
    	for(QuizStatistic stat: statistics) {
    	out.println(createQuizStatBullet(manager, stat));
    	}
    }%>
    </div>
    <br/>
    <div>Top Scores</div>



</body>
</html>