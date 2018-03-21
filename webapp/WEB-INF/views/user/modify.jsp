<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<title>회원 정보 수정</title>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />

		<div id="content">
			<div id="user">
				<h3>회원 정보 수정</h3>
				<form id="join-form" name="joinForm" method="post" action="${pageContext.servletContext.contextPath }/user">
				
					<label class="block-label" for="name">이름</label> 
					<input id="name"name="name" type="text" value="${sessionScope.authUser.name }">
					
					<label class="block-label" for="email">이메일</label> 
					${sessionScope.authUser.email }
					
					<label class="block-label">패스워드</label>
					<input type="password" name="password" value="">

					<fieldset>
						<label class="block-label">성별</label>
						<c:choose> 
							<c:when test='${sessionScope.authUser.gender == "female" }'>
								<label>여</label>
								<input type="radio" name="gender" value="female" checked="checked">
								<label>남</label>
								<input type="radio" name="gender" value="male">
							</c:when> 
							
							<c:when test="${sessionScope.authUser.gender == 'male' }">
								<label>여</label>
								<input type="radio" name="gender" value="female">
								<label>남</label>
								<input type="radio" name="gender" value="male" checked="checked">
							</c:when>				
						</c:choose>
					</fieldset>
					
					<c:if test='${result=="fail" }'>
						<p>수정 실패</p>
					</c:if>

					<input type="submit" value="수정하기">

				</form>
			</div>
		</div>

		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>