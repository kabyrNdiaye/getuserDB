<%@page import="beans.Utilisateur"%>
<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setAttribute("PAGE_TITLE", "Liste des utilisateur");
%>

<%@ include file="include/header.jsp" %>
<section>
	<h1>Lister des utilisateurs</h1>
	<%
	ArrayList<Utilisateur> utilisateurs = (ArrayList<Utilisateur>) request.getAttribute("utilisateurs");
	
	if(utilisateurs.isEmpty())
	{%>
		<p>La liste des utilisateurs est vide !</p><%
	}
	else
	{%>
		<table>
			<tr>
				<th>ID</th>
				<th>Nom</th>
				<th>Prénom</th>
				<th>Login</th>
				<th>Password</th>
				<th colspan="2">Actions</th>
			</tr><%
			for(Utilisateur utilisateur : utilisateurs)
			{%>
				<tr>
					<td><%= utilisateur.getId() %></td>
					<td><%= utilisateur.getNom() %></td>
					<td><%= utilisateur.getPrenom() %></td>
					<td><%= utilisateur.getLogin() %></td>
					<td><%= utilisateur.getPassword() %></td>
					<td><a href="update?id=<%= utilisateur.getId() %>">Modifier</a></td>
					<td><a href="delete?id=<%= utilisateur.getId() %>" onclick="return confirm('En êtes vous sûr ?')">Supprimer</a></td>
				</tr><%
			}%>
		</table><%
	}
	%>
</section>
<%@ include file = "include/footer.jsp"%>
