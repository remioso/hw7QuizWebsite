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
ArrayList<Quiz> quizzes = manager.getQuizzes();
%>
<%! String createQuizBullet(Quiz quiz) {
	String bullet = "<li><a href = 'quizSummary.jsp?id=" + quiz.getQuizId() + "'>" + quiz.getName() + "</a></li>";
	return bullet;
}
	%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="main.css" />
    <script type="text/javascript" src="insertHeader.js"></script>
    <title>Quizzes</title>
</head>

<body>
    <div id="header"></div>
    <h1>Quizzes</h1>
    <%
    for(Quiz quiz: quizzes) {
    	out.println(createQuizBullet(quiz));
    }
    %>
<body>

</body>
</html>