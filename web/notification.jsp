<%-- 
    Document   : notification
    Created on : Sep 24, 2020, 11:20:49 PM
    Author     : Long Le
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Notifications</title>
		<link rel="stylesheet" href="assets/css/bootstrap.min.css">
		<link rel="stylesheet" href="assets/css/customize.css">
    </head>
    <body>
	<!-- Nav bar -->
	<c:set var="account" value="${sessionScope.LOGIN_ACCOUNT}"/>
	<c:url var="notificationUrl" value="DispatchController">
		<c:param name="btAction" value="Notifications"/>
	</c:url>
	<nav class="navbar navbar-dark navbar-expand-sm bg-primary">
		<ul class="navbar-nav mr-auto py-0">
			<li class="nav-item">
				<a class="nav-link "href="home.jsp">Home</a>
			</li>
			<li class="nav-item active">
				<a class="nav-link" href="${notificationUrl}">Notifications</a>
			</li>
		</ul>
		<ul class="navbar-nav ml-auto text-center">
			<!-- Logout form -->
			<li class="nav-item">
				<div class="welcome-user">Welcome, ${account.name}</div>		
			</li>
			<li class="nav-item">
				<form action="DispatchController">
					<input type="submit" value="Logout" name="btAction" class="btn btn-danger btn-sm my-2 my-sm-0 mx-3"/>
				</form>
			</li>
		</ul>
	</nav>

	<c:set var="notifications" value="${requestScope.NOTIFICATIONS}"/>
	<div class="container h-100">
		<c:if test="${not empty notifications}">
			<c:forEach var="noti" items="${notifications}">
			<div class="card my-2">
				<div class="card-header" style="font-weight: bold">
					<f:formatDate value="${noti.date}" type="both"/>
				</div>
				<div class="card-body">	
					<font style="font-weight: bold">${noti.creatorEmail}</font>
					
					<c:if test="${noti.typeId eq 'R'}">			
						reacted to your post: ${noti.content}
					</c:if>
					<c:if test="${noti.typeId eq 'C'}">
						commented to your post: ${noti.content}
					</c:if>
					<br/>
					
					<!-- go to article link -->
					<c:url var="goToArticleUrl" value="DispatchController">
						<c:param name="btAction" value="View Detail"/>
						<c:param name="articleId" value="${noti.articleId}"/>
					</c:url>
					<div class="text-right mt-3">
						<a href="${goToArticleUrl}" class="btn btn-outline-primary">Go to Article</a>
					</div>	
				</div>
			</div>
			</c:forEach>
		</c:if>
		
		<c:if test="${empty notifications}">
			<div class="row h-100 justify-content-center align-items-center">
				<h1>There is literally nothing here ;-;</h1>
			</div>
		</c:if>
	</div>
	<script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
