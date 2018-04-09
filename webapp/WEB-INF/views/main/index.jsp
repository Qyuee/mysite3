<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/main.css" rel="stylesheet" type="text/css">
</head>
<body> 
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="wrapper">
			<div id="content">
				<div id="site-introduction">
					<c:choose>
						<c:when test="${not empty sessionScope.authUser.name }">
							<img id="profile" src="${pageContext.servletContext.contextPath }/assets/images/userImg.jpg" style="width: 30%;">
							<h2>안녕하세요. ${sessionScope.authUser.name } mysite에 오신 것을 환영합니다.</h2>
						</c:when> 
						 
						<c:otherwise>
							<img id="profile" src="${pageContext.servletContext.contextPath }/assets/images/test.jpg" style="width: 100%;">
						</c:otherwise>
					</c:choose>
					
					<p>
						이 사이트는 <span style="color: blue; font-size: large;">이동석</span>의 웹 프로그램밍 실습과제 예제 사이트입니다.<br>
						메뉴는  사이트 소개, 방명록, 게시판이 있구요. JAVA 수업 + 데이터베이스 수업 + 웹프로그래밍 수업 배운 거 있는거 없는 거 다 합쳐서
						만들어 놓은 사이트 입니다.<br><br>
						<a href="#">방명록</a>에 글 남기기<br>
					</p>
				</div>
			</div>
		</div> 
		
		<c:import url="/WEB-INF/views/includes/navigation.jsp" >
			<c:param name="menu" value="main"></c:param> 
		</c:import>
		
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>