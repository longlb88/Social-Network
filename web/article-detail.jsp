<%-- 
    Document   : articleDetail
    Created on : Sep 17, 2020, 9:42:57 PM
    Author     : Long Le
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Article Detail</title>
		<link rel="stylesheet" href="assets/css/bootstrap.min.css">
		<link rel="stylesheet" href="assets/css/customize.css">
    </head>
    <body>
		<!-- Nav bar -->
		<c:set var="account" value="${sessionScope.LOGIN_ACCOUNT}"/>
		<c:url var="notificationUrl" value="DispatchController">
			<c:param name="btAction" value="Notifications"/>
		</c:url>

		<c:set var="article" value="${requestScope.ARTICLE}"/>
		<c:url var="refreshUrl" value="DispatchController">
			<c:param name="btAction" value="View Detail"/>
			<c:param name="articleId" value="${article.articleDTO.articleId}"/>
		</c:url>
		
		<nav class="navbar navbar-dark navbar-expand-sm bg-primary">
			<ul class="navbar-nav mr-auto py-0">
				<li class="nav-item">
					<a class="nav-link"href="home.jsp">Home</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="${notificationUrl}">Notifications</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="${refreshUrl}">Refresh</a>
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
		<!-- Show article -->
		<div class="row h-100 justify-content-center align-items-center">
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
					</div>
					<div class="card-footer">
						<c:set var="reactStatus" value="${requestScope.REACT_STATUS}"/>
						<div style="font-weight: bold">${reactStatus}</div>
						<div class="row">
							<div class="col-6">
								<!-- Show emotion info-->
								<c:set var="likeNumber" value="${requestScope.LIKE}"/>
								<c:set var="dislikeNumber" value="${requestScope.DISLIKE}"/>

								<span class="d-inline-block">
									<span class="text-primary">${likeNumber} Likes</span>											
									<c:url var="urlLike" value="DispatchController">
										<c:param name="btAction" value="Like"/>
										<c:param name="articleId" value="${article.articleDTO.articleId}"/>
									</c:url>
									<a href="${urlLike}"><img src="assets/images/like.png" style="width:40px;height:40px"></a>
								</span>

								<span class="d-inline-block">
									<span class="text-danger">${dislikeNumber} Dislike</span>
									<c:url var="urlDisike" value="DispatchController">
										<c:param name="btAction" value="Dislike"/>
										<c:param name="articleId" value="${article.articleDTO.articleId}"/>
									</c:url>
									<a href="${urlDisike}"><img src="assets/images/dislike.png" style="width:40px;height:40px"></a>
								</span>
							</div>	

							<!-- Delete Article -->
							<c:if test="${account.email eq article.articleDTO.posterEmail}">
								<c:url var="urlDeletePost" value="DispatchController">
									<c:param name="btAction" value="Delete Article"/>
									<c:param name="articleId" value="${article.articleDTO.articleId}"/>
								</c:url>
								
								<div class="col-6 text-right">
									<a class="btn btn-outline-danger" href="${urlDeletePost}" 
									onclick="return confirm('Are you sure you want to delete this article?')">
										Delete Article
									</a> 
								</div>
							</c:if>		
						</div>						
					</div>
				</div>
			</div>
		</div>
		
		<!-- comment form -->
		<c:set var="commentError" value="${requestScope.COMMENT_ERROR}"/>
		<div class="form-group border-bottom">
			<form action="DispatchController" method="POST" >
				<textarea name="txtComment" cols="50" rows="5" placeholder="Limit to 200 chars" class="form-control"></textarea><br/>
				<input type="hidden" name="articleId" value="${article.articleDTO.articleId}" />
				<input type="submit" value="Comment" name="btAction" class="btn btn-success mb-3" />
				<c:if test="${not empty commentError}">
					<span class="text-danger">${commentError.commentLengthError}</span>
				</c:if>
			</form>
		</div>	
		
		<!-- Comments List -->
		<c:set var="commentList" value="${requestScope.COMMENTS_LIST}"/>
		<c:if test="${not empty commentList}">
			<h3>Comments</h3>
			<div class="row justify-content-center align-items-center">
				<c:forEach var="comment" items="${commentList}">
					<div class="col-12">
						<div class="card my-2">
							<div class="card-header" >
								<span style="font-weight: bold">
									${comment.userName}
								</span>
								at <f:formatDate value="${comment.commentDTO.date}" type="both"/>
							</div>

							<div class="card-body">
								${comment.commentDTO.comment} 
								
								<!-- delete comment button section -->
								<c:url var="deleteCommentUrl" value="DispatchController">
									<c:param name="btAction" value="Delete Comment"/>
									<c:param name="commentId" value="${comment.commentDTO.commentId}"/>
									<c:param name="articleId" value="${article.articleDTO.articleId}"/>
								</c:url>	
								
								<div class="text-right">
									<!-- if current user is article owner -->
									<c:if test="${account.email eq article.articleDTO.posterEmail}">
										<a class="btn btn-sm btn-outline-danger" href="${deleteCommentUrl}" 
										onclick="return confirm('Are you sure you want to delete this comment?')">
											Delete comment
										</a>
									</c:if>
									<!-- if current user it not article owner -->    
									<c:if test="${account.email ne article.articleDTO.posterEmail}">
										<!-- if current user is comment owner -->
										<c:if test="${account.email eq comment.commentDTO.commentorEmail}">
										<a class="btn btn-sm btn-outline-danger" href="${deleteCommentUrl}" 
										onclick="return confirm('Are you sure you want to delete this comment?')">
											Delete comment
										</a>
										</c:if>
									</c:if>
								</div>
							</div>
						</div>	
					</div>
				</c:forEach>
			</c:if>
		</div>
	</div>
	<script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
