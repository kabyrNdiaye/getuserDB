<%
    request.setAttribute("PAGE_TITLE", "Modifier un utilisateur");
%>

<%@ include file="include/header.jsp" %>

<section>

    <h1>Modification d'un utilisateur</h1>

    <form method="post">

        <label for="nom">Nom</label>
        <input type="text" name="nom" id="nom" value="${utilisateur.nom}">

        <label for="prenom">Prénom</label>
        <input type="text" name="prenom" id="prenom" value="${utilisateur.prenom}">

        <label for="login">Login</label>
        <input type="text" name="login" id="login" value="${utilisateur.login}">

        <label for="password">Password</label>
        <input type="password" name="password" id="password" value="${utilisateur.password}">

        <input type="submit" value="Modifier">
    </form>

</section>

<%@ include file="include/footer.jsp"%>
