package servers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.fabric.xmlrpc.base.Data;

import models.*;

/**
 * Servlet implementation class CreateAccountServlet
 */
@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccountServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//NOthing
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Attempts to create a new account. On seucces, goes to welcom page. On failure, reports
	 * that the name is already in use
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		String user = request.getParameter("username");
		String pw = request.getParameter("password");
		DataManager dm = (DataManager) context.getAttribute("DataManager");
		if(dm.getUser(user) == null) {
			User new_user = new User(user, dm.getNextId());
			dm.addUser(new_user, DataManager.hashPassword(pw));
			request.getSession().setAttribute("user", new_user);
			RequestDispatcher dispatcher = request.getRequestDispatcher("homepage.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("nameinuse.jsp");
			dispatcher.forward(request, response);
		}
		
	}

}
