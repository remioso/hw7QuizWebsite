<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*"%>
<%@ page import="models.*"%>
<html>
<%DataManager manager = (DataManager)getServletContext().getAttribute("DataManager");
User curUser = (User) session.getAttribute("user");
ArrayList<QuizStatistic> recentlyTakenQuizzes = manager.getRecentUserQuizStats(curUser.getId());
%>
	<%!String createQuizStatBullet(DataManager manager, QuizStatistic stat) {
		String bullet = "<li><a href = 'quiz.jsp?id=" + stat.getQuizId() + "'>" + manager.getQuiz(stat.getQuizId()).getName() + "</a>";
		bullet += "<br/>Date: " + stat.getDate().toString() + " Score: " + stat.getScore() + "</li>";
		return bullet;
	}%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="homepage.css" />
    <link rel="stylesheet" type="text/css" href="main.css" />
    <script type="text/javascript" src="insertHeader.js"></script>
    <title>Quizdom</title>
</head>

<body>
    <div id="header"></div>
    <div id="activity">
        <div>
            <div class="title">Announcements</div>
            <ul>
                <li>announcement</li>
                <li>announcement</li>
                <li>announcement</li>
                <li>announcement</li>
            </ul>
        </div>
        <div>
            <div class="title">Recent Activity</div>
            <ul>
                <li>activity</li>
                <li>activity</li>
                <li>activity</li>
                <li>activity</li>
            </ul>
        </div>
        <div>
            <div class="title">Achievements</div>
            <ul>
                <li>achievement</li>
                <li>achievement</li>
            </ul>
        </div>
    </div>
    <div id="popular"> Popular Quizzes</div>
    <div id="quizzes">
        <div>
            <div class="title">New Quizzes</div>
        </div>
        <div>
            <div class="title">Recently Taken Quizzes</div>
		    <%if(recentlyTakenQuizzes == null) {
		    	out.println("No quizzes taken");
		    } else {
		    	for(QuizStatistic stat: recentlyTakenQuizzes) {
		    	out.println(createQuizStatBullet(manager, stat));
		    	}
		    }%>
        </div>
        <div>
            <div class="title">Quizzes You've Created</div>
            
        </div>
    </div>
</body>

</html>