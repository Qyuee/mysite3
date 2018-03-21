<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
			<div id="board">
				<form class="board-form" method="post" action="${pageContext.servletContext.contextPath }/board">
					<c:choose>
						<c:when test="${not empty pVo }">
							<input type = "hidden" name = "a" value="reply">
							<input type = "hidden" name = "bno" value="${param.bno }" > 
							<input type = "hidden" name = "nowPage" value="${param.nowPage }" >
						</c:when>
						<c:otherwise>
							<input type = "hidden" name = "a" value="write">
							<input type = "hidden" name = "nowPage" value="1" >
						</c:otherwise>
					</c:choose>
					
					<input type = "hidden" name = "no" value="${sessionScope.authUser.no }" />
					<input type = "hidden" name = "kwd" value="${param.kwd }"/>
					
					<c:if test="${not empty result }">
						<span style="color:red; font-weight: bolder;">※글 쓰기에 실패했습니다. 다시 시도해주세요.</span>
					</c:if>  
					
					<table class="tbl-ex">
						<tr>
							<c:choose>
								<c:when test="${not empty pVo }">
									<th colspan="2">[re : ${pVo.title }]<br>답글쓰기</th>
								</c:when>
								<c:otherwise> 
									<th colspan="2">글쓰기</th>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value=""></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<textarea id="content" name="content"></textarea>
							</td>
						</tr>
					</table>
					<div class="bottom">
						<c:choose>
							<c:when test="${not empty pVo }">
								<a href="${pageContext.servletContext.contextPath }/board?a=view&bno=${param.bno }&nowPage=${param.nowPage }&kwd=${param.kwd }">취소</a>
							</c:when>
							<c:otherwise> 
								<a href="${pageContext.servletContext.contextPath }/board?nowPage=${param.nowPage }&kwd=${param.kwd }">취소</a>
							</c:otherwise>
						</c:choose>
						<input type="submit" value="등록">
					</div>
				</form>				
			</div>
		</div>
		
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>