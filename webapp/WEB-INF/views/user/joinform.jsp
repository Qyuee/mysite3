<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> 

<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js" type="text/javascript"></script>
<!-- /* 브라우저가 html을 쭉 보고 dom을 형성하고나서 jquery로 dom객체에 접근 가능. 
 * 그렇기에 모든 elements가 생성되고 접근해야함.
 * 아래의 함수는 dom이 로딩되고 나서 실행되는 함수이다.
 */ -->
<script>
	$(function(){
		$("#btn-checkemail").click(function(){
			var email = $("#email").val();
			if(email == ''){
				return;
			}
			
			// 이제 XMLHttpRequest를 이용하여 Ajax 통신을 하면 됨.
			$.ajax({
				url:"${pageContext.servletContext.contextPath }/api/user/checkemail?email="+email,
				type:"get",
				data:"", 
				dataType:"json",   // 반드시 있어야 함. 헤더에 들어가는 accept 부분.
				success:function(response){
					//console.log(response);
					if(response.result!="success"){
						console.log(response.message);
						return;
					}  
					
					if(response.data=="exist"){ 
						alert("이미 사용중인 이메일 입니다.");
						$("#email").val("").focus();
						
						return; 
					}
					
					$("#ck_email").show();
				},
				error: function(xhr, status, e){
					console.error(status+":"+e);
					
				}
			});
		});
	});
</script>
</head> 
<body> 
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		
		<div id="content"> 
			<div id="user">
				<!-- id는 중복되면 안된다. id로 element를 찾는게 가장 빠르다. -->
				<form:form 
				modelAttribute="userVo"
				id="join-form" name="joinForm" method="post" action="${pageContext.servletContext.contextPath }/user/join">
				
					<label class="block-label" for="name">이름</label> 
					<form:input path="name" />
					<p style="padding:0; font-weight: bold; text-align: left; color:red;">
						<form:errors path="name" /> 
					</p> 

					<label class="block-label" for="email"><img id="ck_email" src="${pageContext.servletContext.contextPath }/images/ck_email.png" style="display: none; width: 10%; " >이메일</label> 
					<form:input path="email" />
					<input type="button" id="btn-checkemail" value="중복체크"> 
					
					<p style="padding:0; font-weight: bold; text-align: left; color:red;">
						<form:errors path="email" /> 
					</p> 
					
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					<%-- <spring:hasBindErrors name="userVo">
						<c:if test="${errors.hasFieldErrors('password') }">  
							<p style="padding:0; text-align: left; color: red;">
								<strong>   
									<spring:message  
									code="${errors.getFieldError( 'password' ).codes[0] }"
									text="${errors.getFieldError( 'password' ).defaultMessage }" />
								</strong> 
						</c:if>
					</spring:hasBindErrors> --%>
					
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="male">
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form:form>
			</div>
		</div> 
		
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>