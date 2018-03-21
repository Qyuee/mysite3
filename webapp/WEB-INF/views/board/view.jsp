<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="6">글보기</th>
					</tr>
					 
					<tr>
						<td class="label">제목</td>
						<td>${vo.title }</td> 
					</tr>
					
					<tr>
						<td class="label">작성자</td>
						<td>${vo.uname }</td> 
						<td class="label">작성일</td> 
						<td>${vo.createdDate }</td>
					</tr>
					
					<tr>
						<td class="label" colspan="1">내용</td>
						<td colspan="5">
							<div class="view-content">
								${vo.content }
							</div>
						</td> 
					</tr>
				</table> 
				<div class="bottom"> 
					<a href="${pageContext.servletContext.contextPath }/board?nowPage=${param.nowPage }&kwd=${param.kwd }">글목록</a>
					<c:if test="${sessionScope.authUser.no == vo.no }">  
						<a href="${pageContext.servletContext.contextPath }/board?a=modifyform&bno=${vo.bno }&nowPage=${param.nowPage }&kwd=${param.kwd }">글수정</a>
					</c:if> 
					<c:if test="${not empty sessionScope.authUser.no }">
						<a href="${pageContext.servletContext.contextPath }/board?a=replyform&bno=${vo.bno }&nowPage=${param.nowPage }&kwd=${param.kwd }">답글 달기</a>
					</c:if>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>