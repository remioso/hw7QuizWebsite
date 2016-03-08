package servers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.*;

/**
 * Servlet implementation class MessageServlet
 */
@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MessageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		HttpSession session = request.getSession();
		User curUser = (User)session.getAttribute("user");
		DataManager dataManager = (DataManager) context.getAttribute("DataManager");

		if(request.getParameter("friendRequest") == null) {
		String recipientUsername = request.getParameter("recipientUsername");
		String messageText = request.getParameter("message");
			if (dataManager.getUser(recipientUsername) != null) {
				User recipient = dataManager.getUser(recipientUsername);
				Message message = new Message(recipient.getId(), curUser.getId(), messageText, dataManager.getNextId());
				dataManager.addMessage(message);
				RequestDispatcher dispatcher = request.getRequestDispatcher("composemessage.jsp?success=true");
				dispatcher.forward(request, response);
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("composemessage.jsp?success=false");
				dispatcher.forward(request, response);
			}
		} else {
			Integer requestedFriendId = Integer.parseInt(request.getParameter("requestedFriendId"));
			Message friendRequest = new Message(requestedFriendId, curUser.getId(), dataManager.getNextId());
			dataManager.addMessage(friendRequest);
			RequestDispatcher dispatcher = request.getRequestDispatcher("user.jsp?id=" + requestedFriendId);
			dispatcher.forward(request, response);
		}


	}

}
