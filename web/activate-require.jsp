<%-- 
    Document   : activate-require
    Created on : Sep 27, 2020, 4:45:34 PM
    Author     : Long Le
--%>
 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Require Activate</title>
		<link rel="stylesheet" href="assets/css/bootstrap.min.css">
		<link rel="stylesheet" href="assets/css/customize.css">
    </head>
    <body>
	<!-- Nav bar -->
	<c:set var="account" value="${sessionScope.LOGIN_ACCOUNT}"/>
	<nav class="navbar navbar-dark navbar-expand-sm bg-primary">
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
	
	<div class="container-fluid h-100">
			<div class="row h-100 justify-content-center align-items-center">
				<div class="col-10 col-md-8 col-lg-6 text-center">
					<img src="assets/images/warning.png" alt="warning" width="200" height="200"> <br/><br/>
					<h1 class="my-3">Your account wasn't activated!</h1>
					<a href="DispatchController?btAction=Send Activation" class="btn btn-primary">Send Activation Code</a>
				</div>
			</div>
		</div>
	<script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
