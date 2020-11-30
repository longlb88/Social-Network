<%-- 
    Document   : home
    Created on : Sep 15, 2020, 11:48:05 PM
    Author     : Long Le
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Home Page</title>
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
					<a class="nav-link active"href="#">Home</a>
				</li>
				<li class="nav-item">
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
	
		<div class="container">
			<!-- Search form -->
			<c:set var="searchValue" value="${param.txtSearch}"/>
				<div class= "my-4">
					<form action="DispatchController" class="form-inline"/> 
						<input type="text" name="txtSearch" value="${searchValue}" class="form-control col-10" placeholder="Search by content"/>
						<input type="submit" value="Search" name="btAction" class="btn btn-success col-2" /> 
						<input type="hidden" name="page" value="1" /> 
						<!-- default page when search is 1 -->
					</form> 
				</div>
			
			<!-- Post article form -->
			<c:set var="error" value="${requestScope.POST_ARTICLE_ERRORS}"/>
			<div class="card">
				<p class="card-header" style="font-weight: bold">What is in your mind?</p>
				<div class="m-3">
					<form action="DispatchController" method="POST" enctype="multipart/form-data" >
						<div class="form-group">
							<label>Title</label>
							<input type="text" name="txtTitle" value="" placeholder="(eg 1 - 200 chars)" class="form-control"/>
						</div>
						<div class="form-group">
							<label>Description</label>
							<textarea name="txtDescription" rows="5" cols="50" placeholder="(eg 1 - 2000 chars)" class="form-control"></textarea>
						</div>
						<div class="form-group">
							<label>Choose an image</label>
							<input type="file" accept="image/*" name="image" class="form-control-file"/>
						</div>	
						<div class="text-right">
							<input type="submit" value="Post Article" name="btAction" class="btn btn-success" />
						</div>
					</form>	
				</div>
			</div>
			<br/><br/>
			
			<!-- Display result -->
			<c:if test="${not empty searchValue}">
				<c:set var="result" value="${requestScope.ARTICLE_LIST}"/>
				<c:set var="totalPages" value="${requestScope.TOTAL_PAGES}"/>
				
				<c:if test="${not empty result}">
					<h3 class="text-center">POSTS</h3>
					<!-- Display articles -->
					<div class="row justify-content-center align-items-center">
						<c:forEach var="article" items="${result}" varStatus="counter">	
							<div class="col-12">
								<div class="card my-3">
									<div class="card-header" >
										<span style="font-weight: bold">
											${article.userName}
										</span>
										at <f:formatDate value="${article.articleDTO.date}" type="both"/>
									</div>
									<div class="card-body">
										<c:if test="${not empty article.articleDTO.image}">
											<img class="card-img-top mb-2" src="data:image/jpeg;base64,${article.articleDTO.image}"/>
										</c:if>

										<h4 class="card-title">
											${article.articleDTO.title}
										</h4>
										${article.articleDTO.description}
															
										<!-- View Article Detail url -->
										<c:url var="urlDetail" value="DispatchController">
											<c:param name="btAction" value="View Detail"/>
											<c:param name="articleId" value="${article.articleDTO.articleId}"/>
										</c:url>

										<!-- Delete Article url -->
										<c:url var="urlDeletePost" value="DispatchController">
											<c:param name="btAction" value="Delete Article"/>
											<c:param name="articleId" value="${article.articleDTO.articleId}"/>
											<c:param name="txtSearch" value="${searchValue}"/>
										</c:url>

										<div class="text-right">
											<div class="btn-group">
												<!-- View Article Detail url -->
												<a class="btn btn-outline-success" href="${urlDetail}">View detail</a> 
												
												<!-- Delete Article -->
												<c:if test="${account.email eq article.articleDTO.posterEmail}">
														<a class="btn btn-outline-danger" href=${urlDeletePost}" 
													onclick="return confirm('Are you sure you want to delete this article?')">
															Delete Article
														</a>
												</c:if>
											</div>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
					
					<!-- get current page -->
					<c:set var="curPage" value="${param.page}"/>

					<!-- Page indicator -->
					<nav>
						<ul class="pagination justify-content-center mt-4">
							<c:forEach var="page" begin="1" end="${totalPages}" step="1">
							<c:url var="urlPaging" value="DispatchController">
								<c:param name="btAction" value="Search"/>
								<c:param name="txtSearch" value="${searchValue}"/>
								<c:param name="page" value="${page}"/>
							</c:url>
							
							<c:if test="${curPage eq page}">
								<li class="page-item active">
									<a class="page-link" href="${urlPaging}">${page}</a>
								</li>
							</c:if>
							<c:if test="${curPage ne page}">
								<li class="page-item">
									<a class="page-link" href="${urlPaging}">${page}</a>
								</li>
							</c:if>
							</c:forEach>
						</ul>
					</nav>	
				</c:if>
				
				<c:if test="${empty result}">
					<h2 class="text-center">No article found!!!</h2>
				</c:if>
			</c:if>
		</div>
		<script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
