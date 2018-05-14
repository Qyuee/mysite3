<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script type="text/javascript">
//이제 XMLHttpRequest를 이용하여 Ajax 통신을 하면 됨.
/* function insert(){ 
	var params=$('#add-form').serialize();
	
	$.ajax({
		url:"${pageContext.servletContext.contextPath }/guestbookajax/insert",
		type:"POST",
		data:params, 
		dataType:"json",   // 반드시 있어야 함. 헤더에 들어가는 accept 부분.
		success:function(response){
			if(response.data=="fail"){
				alert("등록 실패");
				return;
			}  
			
			if(response.data=="success"){
				$('#add-form')[0].reset();
				getlist();
				
				// 카테고리 리스트 재부팅 
				$('#input-name').focus(); 
				return;
			}
		}, 
		error: function(xhr, status, e){
			
		}
	});
} */

// jQuery 플러그인 만들기
(function($){
	$.fn.hello=function(){
		var $element=$(this);
		console.log($element.attr("id")+": hello!!");
	}
	
})(jQuery);

var ejsListItem=new EJS({
	url:"${pageContext.request.contextPath }/assets/js/ejs/templete/templete"
});

var fetchList=function(){
	if(isEnd==true){
		return; 
	}
	
	// 현재 리스트에서 가장 마지막 li에 있는 data-no값을 추출.
	var index = $('#list-guestbook li').last().data('no') || 0;
	
	$.ajax({
		url:"${pageContext.servletContext.contextPath }/guestbookajax/getlistAjax?startNo="+index,
		type:"GET",
		dataType:"json",   // 반드시 있어야 함. 헤더에 들어가는 accept 부분. 
		success:function(response){
			console.log(response);
			
			var str="";
			if(response.result != "success"){
				console.error(response.message);
				return;
			}
			
			// 끝 감지
			if(response.data.length < 5){
				alert("마지막 데이터 입니다.");
			}
			
			var data=response.data;
			$.each(data, function(index, vo){
				render(false, vo);
			});
		}, 
		error: function(xhr, status, e){
			
		}
	});
}

var messageBox=function(title, message, callback){
	$( "#dialog-message" ).attr("title", title);
	$( "#dialog-message p" ).text(message);
	$( "#dialog-message" ).dialog({
    	modal: true,
     	buttons: {
        	"확인": function() {
          	$( this ).dialog( "close" );
        	}
      	},
      	close: callback || function(){} 
    });
}

var render=function(mode, vo){
	var str=ejsListItem.render(vo);
	
	if(mode==true){
		$("#list-guestbook").prepend(str);
	}else{  
		$("#list-guestbook").append(str);
	}
}

var isEnd=false;

$(function(){
	
	deleteDialog = $( "#dialog-delete-form" ).dialog({
    	autoOpen: false,
    	height: 400,
      	width: 350,
      	modal: true,
      	buttons: {
        	"삭제" : function(){
        		// 삭제 처리 ajax 실행!.
        		var password=$("#password-delete").val();
        		var no=$("#hidden-no").val();
        		
        		$.ajax({
        			url:"${pageContext.servletContext.contextPath }/guestbookajax/delete",
        			type:"post",
        			dataType:"json",   // 반드시 있어야 함. 헤더에 들어가는 accept 부분.
        			data:"no="+no+"&password="+password,
        			success:function(response){
        				
        				if(response.result=="fail"){
        					return;
        				}
        				
        				if(response.data==-1){
        					$(".validateTips.normal").hide();
        					$(".validateTips.error").show();
        					$("#password-delete").val();
        					return;
        				}
        				
        				$("#list-guestbook li[data-no="+response.data+"]").remove();
        				
        				console.log("response:"+response);
        				deleteDialog.dialog( "close" );
        			},
        			error:function(){
        				console.log("error");
        			}
        		});
        		
        	},
        	"취소": function() {
        		deleteDialog.dialog( "close" );
       		}
      	},
      	//취소 버튼 눌렀을 때 후 처리.
      	close: function() {
      		$("#password-delete").val("");
      		$("#hidden-no").val("");
      		$(".validateTips.normal").show();
			$(".validateTips.error").hide();
      	}
    });
	
	// Live 이벤트 리스너
	$(document).on("click", "#list-guestbook li a", function(event){
		event.preventDefault();
		var no=$(this).data("no");
		$("#hidden-no").val(no);
		
		deleteDialog.dialog("open");
	});
	
	$("#add-form").submit(function(event){
		event.preventDefault(); // form 실행 중지
		var queryString = $(this).serialize();
		
		var data={};
		$.each($(this).serializeArray(), function(index, o){
			data[o.name] = o.value;
		});
		
		if(data["name"] == ''){
			messageBox("메세지 등록", "ID가 없습니다.", function(){
				$("#input-name").focus();
			});
			
			return;
		}
		
		if(data["password"] == ''){
			messageBox("메세지 등록", "패스워드는 필수 입력 사항입니다.", function(){
				$("#input-password").focus();
			});
			return;
		}
		
		if(data["content"] == ''){
			messageBox("메세지 등록", "내용이 비어있습니다.", function(){
				$("#tx-content").focus();
			});
			return;
		}
		
		console.log(data);
		console.log(JSON.stringify(data)); 
		// 배열 형태로 form내의 값을 추출한다.
		//console.log($(this).serializeArray()); 
		
		$.ajax({
			url:"${pageContext.servletContext.contextPath }/guestbookajax/insert",
			type:"POST",
			data: JSON.stringify(data), // json data를 String으로 변환. 
			dataType:"json",   			// 반드시 있어야 함. 헤더에 들어가는 accept 부분.
			contentType:"application/json",  //
			success : function(response){
				console.log(response);
				render(true, response.data);
				$('#add-form')[0].reset();
			},
			error : function(){
				
			}
		});
	});
	
	$("#btn-fetch").click(function(){
		fetchList(); 
	});
	
	$(window).scroll(function(){
		var $window=$(this);
		var scrollTop = $window.scrollTop();  // 
		var windowHeight=$window.height();    // 윈도우 높이
		var documentHeight = $(document).height();  
		
		//console.log(scrollTop +":" + documentHeight + ":" + windowHeight);
		
		// documentHeight = scrollTop + windowHeight
		// scrollbar의 Thumbs가 바닥에 도달하기 30px 전이라는 의미
		if(scrollTop + windowHeight + 30 > documentHeight){
			fetchList(); 
		}
	});
	
	$("#content").hello();
	
});

/* function delete_proc(){
	var params=$('#delete-form').serialize();
	$.ajax({
		url:"${pageContext.servletContext.contextPath }/guestbookajax/delete",
		data: params,
		type:"GET",
		dataType:"json",   // 반드시 있어야 함. 헤더에 들어가는 accept 부분.
		success:function(response){
			if(response != null){
				if(response.result=='success'){
					getlist();
					$('#delete-form')[0].reset();
					$('#dialog-delete-form').css("display", "none");
					return;
				}
			}else{
				alert("리스트 데이터가 null 입니다.");	
			}
		}, 
		error: function(xhr, status, e){
			
		}
	});
} */


 
/* function delete_form(no){
	var delete_form=$('#dialog-delete-form');
	delete_form.css("display", "");
	delete_form.css("position", "absolute");
	delete_form.css("padding", "20px");
	delete_form.css("background-color", "#e6e6e6");
	delete_form.center();
	$('#delete-form').children('#hidden-no').val(no);
	$('#delete-form')[0].reset();
} */

/* function hide_form(element){ 
	$(element).parent().hide();  
} */

// 화면 중앙 계산 및 정렬
/* jQuery.fn.center=function(){
	this.css("top", Math.max(0, (($(window).height()-$(this).outerHeight())/2) + $(window).scrollTop())-30+"px");
	this.css("left", Math.max(0, (($(window).width()-$(this).outerWidth())/2) + $(window).scrollLeft())+"px");
	return this;
} */

</script>

</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook"> 
				<h1><img id='title-img' src="${pageContext.request.contextPath }/assets/images/guestbook.png">방명록</h1>  
				<form id="add-form" method="post"> 
					<input type="text" id="input-name" name='name' placeholder="이름">
					<input type="password" id="input-password" name='password' placeholder="비밀번호"> 
					<textarea id="tx-content" placeholder="내용을 입력해 주세요." name='content'></textarea>
					<input id="btn-send" type="submit" value="등록하기" />
				</form>  
				
				<ul id="list-guestbook">
					
				</ul>
				
			</div>
			
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none; border: 1px solid #808080; border-radius: 10px;">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				
  				<form id='delete-form' style="display: inline;">
  					<input type="hidden" id="hidden-no" name="no" value="">
 					<input type="password" id="password-delete" name='password' value="" class="text ui-widget-content ui-corner-all">
					<input type="submit" tabindex="-1" style="position: absolute; top:-1000px;" value='삭제'>
  				</form>
			</div>
			
			<div style="text-align: center;">
				<button id="btn-fetch">더보기</button>
			</div>
			
			<div id="dialog-message" title="테스트" style="display:none">
  				<p>테스트 입니다.</p>
			</div>
			
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/> 
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>