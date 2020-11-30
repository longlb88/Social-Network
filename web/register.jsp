<%-- 
    Document   : register
    Created on : Sep 15, 2020, 10:50:32 PM
    Author     : Long Le
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Register Page</title>
		<link rel="stylesheet" href="assets/css/bootstrap.min.css">
		<link rel="stylesheet" href="assets/css/customize.css">
    </head>
    <body>
		<c:set var="errors" value="${requestScope.CREATE_ERRORS}"/>
		<div class="container-fluid h-100 bg-custom">
			<div class="row h-100 justify-content-center align-items-center">
				<div class="card p-5 col-10 col-md-8 col-lg-6">
					<h1 class="text-center">Sign up</h1>
					<form action="DispatchController" method="POST" class="form-group">
						<label>Email</label>
						<input type="text" name="txtEmail" value="" class="form-control" placeholder="example@gmail.com"/>
						<c:if test="${not empty errors.emailExisted}">
							<p class="text-danger">${errors.emailExisted}</p>
						</c:if>
						
						<label>Full name</label>
						<input type="text" name="txtFullname" value="" class="form-control" placeholder="2-50 chars"/>
						<c:if test="${not empty errors.fullNameLengthError}">
							<p class="text-danger">${errors.fullNameLengthError}</p>
						</c:if>
						
						<label>Password</label>
						<input type="password" name="txtPassword" value="" class="form-control" placeholder="6-30 chars"/>
						<c:if test="${not empty errors.passwordLengthError}">
							<p class="text-danger">${errors.passwordLengthError}</p>
						</c:if>
						
						<label>Confirm</label>
						<input type="password" name="txtConfirm" value="" class="form-control"/>
						<c:if test="${not empty errors.confirmNotMatched}">
							<p class="text-danger">${errors.confirmNotMatched}</p>
						</c:if>
						
						<br/>
						<div class="text-center">
							<input type="submit" value="Create Account" name="btAction" class="btn btn-primary"/> <br/>
						</div>
					</form>
					Already have account? <a href="login.html">Sign in</a>
				</div>
			</div>
		</div>
	<script src="assets/js/bootstrap.min.js"></script>
	</form>
    </body>
</html>
