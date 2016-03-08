package servers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.*;

/**
 * Servlet implementation class UserSearchServlet
 */
@WebServlet("/UserSearchServlet")
public class UserSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSearchServlet() {
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
		String searchString = request.getParameter("searchString");
		HttpSession session = request.getSession();
		DataManager manager = (DataManager) getServletContext().getAttribute("DataManager");
		System.out.println(searchString);
		if(searchString == null) { //get all users 
			ArrayList<User> allUsers = manager.getUsers();
			System.out.println(session);
			session.setAttribute("searchresults", allUsers); 
			RequestDispatcher dispatcher = request.getRequestDispatcher("searchresults.jsp");
			dispatcher.forward(request, response);	
		} else {
			ArrayList<User> searchResults = manager.searchUsers(searchString);
			session.setAttribute("searchresults", searchResults);
			RequestDispatcher dispatcher = request.getRequestDispatcher("searchresults.jsp");
			dispatcher.forward(request, response);	
		}
		doGet(request, response);
	}

}
