<%@ page language="java" pageEncoding="UTF-8"%>
<%
    request.setAttribute("PAGE_TITLE", "Ajouter un utilisateur");
%>

<%@ include file="include/header.jsp" %>
<%@ page import="beans.Utilisateur"%>

<section>
	<h1>Ajout d'un utilisateur</h1>
	<form method="post">
		<label for="nom">Nom</label>
		<input type="text" name="nom" id="nom">
		
		<label for="prenom">Prénom</label>
		<input type="text" name="prenom" id="prenom">
		
		<label for="login">Login</label>
		<input type="text" name="login" id="login">
		
		<label for="password">Password</label>
		<input type="password" name="password" id="password">
		
		<input type="submit" value="Ajouter">
	</form>
</section>
<%@ include file = "include/footer.jsp"%>

