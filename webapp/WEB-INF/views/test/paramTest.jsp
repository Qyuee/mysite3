<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>HandlerMethodArgumentResolver Test1</h1>
	<span>내가 원하는 @어노테이션을 만들고 사용.</span><br>
	
	<form method="POST" action="${pageContext.servletContext.contextPath }/test/hmar1">
		first : <input type="text" name="first" value=""/>
		second : <input type="text" name="second" value=""/>
		third : <input type="text" name="third" value=""/>
		
		<input type="submit" value="go" /> 
	</form>
</body>
</html>