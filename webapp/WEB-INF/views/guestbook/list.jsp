<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body> 
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<form action="${pageContext.servletContext.contextPath }/guestbook" method="post">
					<table>
						<tr>
							<c:choose>
								<c:when test="${not empty sessionScope.authUser.name }">
									<td>이름</td><td>${sessionScope.authUser.name }</td>
									<td>비밀번호</td><td><input type="password" name="password"></td>
								</c:when>
								 
								<c:otherwise>
									<td>이름</td><td><input type="text" name="name"></td>
									<td>비밀번호</td><td><input type="password" name="password"></td>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				
				<c:choose>
					<c:when test="${not empty param.result }">
						<span style="color:red;">삭제를 실패 했습니다.</span>
					</c:when> 
				</c:choose>
				 
				<ul> 
					<li>
						<c:set var="count" value="${fn:length(list) }" />
						<c:forEach items="${list }" var="vo" varStatus="status" >
							<table>
								<tr>
									<td>[${count-status.index }]</td> <!-- 주석 --> 
									<td>${vo.name }</td> 
									<td>${vo.to_date }</td>
									<td><a href="${pageContext.servletContext.contextPath }/guestbook/del?no=${vo.no }">삭제</a></td>
								</tr>
								<tr>
									<td colspan=4>
									${vo.content }
									</td>
								</tr>
							</table>
							<br>
						</c:forEach>
						
					</li>
				</ul> 
			</div>
		</div>
		
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook" />
		</c:import>
		
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>