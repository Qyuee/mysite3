<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script>
	$(function(){
		$("#navigation > ul > li[class!='selected']").mouseover(function(){
			$(this).css({
				"background-color" : "red",
				"font-size" : "1.1em"
			});
			
			$("a", this).css({
				"color" : "white"
			});
		}).mouseout(function(){
			$(this).css({
				"background-color" : "#E9E9E9",
				"font-size" : "1em"
			});
			
			$("a", this).css({
				"color" : "#4F4F4F"
			});
		});
	});
</script>

<div id="navigation">
	<ul>
		<c:choose>
			<c:when test="${param.menu == 'main' }">
				<li class="selected"><a href="${pageContext.servletContext.contextPath }/main">main</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/guestbook">방명록</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/guestbookajax">방명록(ajax)</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/board?nowPage=1">게시판</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/gallery">갤러리</a></li>
			</c:when>
			
			<c:when test="${param.menu == 'guestbook' }">
				<li><a href="${pageContext.servletContext.contextPath }/main">main</a></li>
				<li class="selected"><a href="${pageContext.servletContext.contextPath }/guestbook">방명록</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/guestbook/ajax">방명록(ajax)</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/board?nowPage=1">게시판</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/gallery">갤러리</a></li>
			</c:when> 
			
			<c:when test="${param.menu == 'board' }"> 
				<li><a href="${pageContext.servletContext.contextPath }/main">main</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/guestbook">방명록</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/guestbookajax">방명록(ajax)</a></li>
				<li class="selected"><a href="${pageContext.servletContext.contextPath }/board?nowPage=1">게시판</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/gallery">갤러리</a></li>
			</c:when>
			
			<c:when test="${param.menu == 'guestbook-ajax' }"> 
				<li><a href="${pageContext.servletContext.contextPath }/main">main</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/guestbook">방명록</a></li>
				<li class="selected"><a href="${pageContext.servletContext.contextPath }/guestbookajax">방명록(ajax)</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/board?nowPage=1">게시판</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/gallery">갤러리</a></li>
			</c:when>
			
			<c:when test="${param.menu == 'gallery' }"> 
				<li><a href="${pageContext.servletContext.contextPath }/main">main</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/guestbook">방명록</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/guestbookajax">방명록(ajax)</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/board?nowPage=1">게시판</a></li>
				<li class="selected"><a href="${pageContext.servletContext.contextPath }/gallery">갤러리</a></li>
			</c:when>
			   
			<c:otherwise>   
				<li><a href="${pageContext.servletContext.contextPath }/main">main</a></li> 
				<li><a href="${pageContext.servletContext.contextPath }/guestbook">방명록</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/guestbookajax">방명록(ajax)</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/board?nowPage=1">게시판</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/gallery">갤러리</a></li>
			</c:otherwise> 
			
		</c:choose>
	</ul>
</div>