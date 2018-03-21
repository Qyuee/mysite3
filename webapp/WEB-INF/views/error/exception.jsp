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
	<h1>예상치 못한 에러 입니다.</h1>
	<h4>==================================================================</h4>
	<p style="color: #ff0000;">${errors }</p>
	<h4>==================================================================</h4>
	<p style="color: blue;">${errors2 }</p>
	<a href="#" onclick="history.go(-1);">이전 작업으로 가기</a>
</body>
</html>