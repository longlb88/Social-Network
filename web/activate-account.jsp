<%-- 
    Document   : activate-account
    Created on : Sep 27, 2020, 3:32:31 PM
    Author     : Long Le
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Activate Account</title>
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
				<div class="col-8 col-md-6 col-lg-4 text-center">
					<h1>Activate Account</h1><br/>
					<!-- Confirm activate form -->
					<form action="DispatchController" method="POST" class="form-group">
						<input type="hidden" name="generatedCode" value="${requestScope.ACTIVATION_CODE}" />
						<label>Enter the 6 digits code sent to your email (${account.email})!</label>
						<input type="text" name="txtActivationCode" value="" class="form-control"/><br/>
						<input type="submit" value="Activate Account" name="btAction" class="btn btn-primary"/> <br/>
					</form>
					<c:set var="error" value="${requestScope.ACTIVATE_ERROR}"/>
					<c:if test="${not empty error}">
						<font color="red">${error.wrongCodeError}</font>
					</c:if>
					<br/>  

					<!-- Resend activate code -->
					<a href="DispatchController?btAction=Send Activation">Resend activation code</a>

				</div>
			</div>
		</div>
		<script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
