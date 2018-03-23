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
			<div id="board"> 
				<form id="search_form" action="${pageContext.servletContext.contextPath }/board" method="GET">
					<button class="btn_all" type="button" onclick="location='${pageContext.servletContext.contextPath }/board?nowPage=1'">전체보기</button>
					<input type="text" id="kwd" name="kwd" value="${param.kwd }">
					<input type="hidden" name='nowPage' value="1"> 
 					<input type="submit" value="찾기">
				</form>
				 
				<c:if test="${not empty param.result }">
					<span style="color: red; font-weight: bolder;">※글 삭제에 실패했습니다.</span>
				</c:if>
				
				<c:if test="${not empty totalCount && not empty param.kwd }">
					<span>[검색 키워드] - </span> 
					<c:choose>
						<c:when test="${param.kwd !='' }">
							<span>${param.kwd }</span><br/> 
						</c:when> 
						<c:otherwise>
							<span>없음</span><br/>
						</c:otherwise>
					</c:choose>
					<span>[검색 결과] - ${totalCount }개</span><br/> 
				</c:if>
				
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					
					<!-- 반복 시작 -->
					<c:set var="count" value="${fn:length(list) }" /> 
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							<td>${totalCount - (nowPage-1)*5 - status.index }</td> 
							<td style="text-align: left;">
								<c:if test="${vo.depth > 0 }">
									<img src="${pageContext.servletContext.contextPath }/images/reply.png" style="padding-left: ${vo.depth*20}px;"> 
								</c:if>
								<a href="${pageContext.servletContext.contextPath }/board/view?bno=${vo.bno }&nowPage=${param.nowPage }&kwd=${param.kwd }">${vo.title }</a></td>
							<td>${vo.uname }</td> 
							<td>${vo.cnt }</td>
							<td>${vo.createdDate }</td> 
							<td>
								<c:if test="${sessionScope.authUser.no == vo.no }">
									<a href="${pageContext.servletContext.contextPath }/board/delete?bno=${vo.bno }&nowPage=${param.nowPage }&kwd=${param.kwd }" class="del">삭제</a>
								</c:if> 
							</td> 
						</tr>
					</c:forEach>
					<!-- 반복 종료 -->
					
				</table>
				
				<div class="pager">
					<ul>
						<!-- <li><a href="">◀</a></li>
						<li><a href="">1</a></li>
						<li><a href="">2</a></li>
						<li class="selected">3</li>
						<li><a href="">4</a></li>
						<li>5</li>
						<li><a href="">▶</a></li> -->
						<c:forEach items="${paging }" var="page" varStatus="status">
							<c:choose>
								<c:when test="${param.nowPage == page.key }">
									<li class="selected">[${page.key }]</li>
								</c:when>
								<c:when test="${page.value=='none' }">
									<li style="color:#cec9c9;">[${page.key }]</li> 
								</c:when>
								<c:otherwise>
									<li><a href="${page.value }">[${page.key }]</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						
					</ul>
				</div>				
				<div class="bottom"> 
					<c:if test="${not empty sessionScope.authUser.no }">
						<a href="${pageContext.servletContext.contextPath }/board/write?nowPage=${param.nowPage }&kwd=${param.kwd }" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>  
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>