package servlets;

import java.io.IOException;

import beans.Utilisateur;
import dao.UtilisateurDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateUser
 */
@WebServlet("/update")
public class UpdateUser extends HttpServlet
{
	private static final long	serialVersionUID	= 1L;
	private static final String	UPDATE_USER_FORM	= "/WEB-INF/modifierUtilisateur.jsp";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String id = request.getParameter("id");

		if (id != null && id.matches("[0-9]+"))
		{
			Utilisateur utilisateur = UtilisateurDao.get(Integer.parseInt(id));

			if (utilisateur != null)
			{
				request.setAttribute("utilisateur", utilisateur);
				getServletContext().getRequestDispatcher(UPDATE_USER_FORM).forward(request, response);
				return;
			}
		}

		response.sendRedirect("list");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String id = request.getParameter("id");
		String login = request.getParameter("login");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String password = request.getParameter("password");

		Utilisateur utilisateur = new Utilisateur(Integer.parseInt(id), nom, prenom, login, password);

		UtilisateurDao.modifier(utilisateur);

		response.sendRedirect("list");
	}

}
