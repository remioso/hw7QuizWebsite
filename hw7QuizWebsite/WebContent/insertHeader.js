window.onload = function() {
	var elem = document.getElementById("header");
	if(elem) {
		var html = "<h1>\n<div id='title'><a href='homepage.jsp'>Quizdom</a></div>";
		html += "\n<div class='search'>\n<form action='UserSearchServlet' method='post'>";
		html += "\nSearch for a user: \n<input type='text' name='searchString' />\n<input type='submit' value='Search' />";
		html += "\n</form>\n</div>\n<div class='messages'><a href='messages.jsp'>Messages</a></div>";
		html += "\n<div class='logout'><form action='LogoutServlet' method='post'><input type='submit' value='Log Out'/></form></div>";
	    html += "\n</h1>\n<div>\n\n<table id='toolbar'>\n<tr>";
	    html += "\n<td class='menu'><a class='tool' href='quizzes.jsp'> Quizzes</a></td>\n<td class='menu'><a class='tool' href='createquiz.jsp'>Create</a></td>";
	    html += "\n<td class='menu'><a class='tool' href='account.jsp'>Account</a></td>\n</tr>\n</table>\n</div>";
	    elem.innerHTML = html;
	}
}