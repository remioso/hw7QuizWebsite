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

import models.DataManager;
import models.Message;
import models.User;

/**
 * Servlet implementation class AddFriendServlet
 */
@WebServlet("/AddFriendServlet")
public class AddFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddFriendServlet() {
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
		Message friendRequest = dataManager.getMessage(Integer.parseInt(request.getParameter("messageId")));
		User requester = dataManager.getUser(friendRequest.getSenderId());
		if(request.getParameter("requeststatus").equals("Accept")) {
			dataManager.addFriend(curUser.getId(), requester.getId());
			//TODO: delete friend request
			RequestDispatcher dispatcher = request.getRequestDispatcher("messages.jsp");
			dispatcher.forward(request, response);
		} else {
			//TODO: delete friend request
		}
	}

}
