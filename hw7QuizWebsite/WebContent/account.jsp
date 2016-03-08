<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.*"%>
<%@ page import="models.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<% DataManager manager = (DataManager) getServletContext().getAttribute("DataManager");
	User curUser = (User) session.getAttribute("user");
	ArrayList<Integer> friendsList = new ArrayList<Integer>();
	ArrayList<QuizStatistic> statistics = new ArrayList<QuizStatistic>();
	if(curUser == null) {
		response.sendRedirect("index.jsp");
	} else {
		friendsList = manager.getFriends(curUser.getId());
		statistics = manager.getAllUserQuizStats(curUser.getId());
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
	<script type="text/javascript" src="insertHeader.js"></script>
	<link rel="stylesheet" type="text/css" href="main.css" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Your Account</title>
</head>
<body>

    <div id="header"></div>
    
    <h1><%=curUser.getUsername()%></h1>

    <div>
    <b>Friends</b> 
    <br/>
    <%if(friendsList.isEmpty()) {
    	out.println("you have no friends :(");
    } else {
    	for(Integer friendID: friendsList) {
    	out.println(createFriendBullet(manager.getUser(friendID)));
    	}
    }%>
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



</body>
</html>