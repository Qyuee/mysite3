<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%
	pageContext.setAttribute("newLine", "\n");
%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/gallery.css"
	rel="stylesheet" type="text/css"> 
<link href="${pageContext.request.contextPath }/assets/css/lightbox.css"
	rel="stylesheet" type="text/css">
<link href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"
	rel="stylesheet" type="text/css">
	
<script type="text/javascript" 
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" 
	src="${pageContext.request.contextPath }/assets/js/lightbox.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script type="text/javascript">

	$(document).ready(function(){
		var list = new Array(); 
		<c:forEach items="${files}" var="files">
			list.push("${files}");
		</c:forEach>
		
		// 업로드 다이알로그
		var dialogUpload = $("#dialog-upload-form").dialog({
			autoOpen : false,
			height : 600,
			width : 700,
			modal : true,
			buttons : {
				"업로드" : function() {
					$("#dialog-upload-form form").submit();
					$(this).dialog("close");
				},
				"취소" : function() {
					$(this).dialog("close");
				}
			},
			close : function() {
				$("#dialog-upload-form form").get(0).reset(); 
				$("#image-preview").attr("src", "${pageContext.request.contextPath }/assets/images/no_image.png");
				$("#image-preview").css({
					"width" : "100%",
					"height" : "100%"
				}); 
			}
		});

		$("#upload-image").click(function(event) { 
			event.preventDefault();
			dialogUpload.dialog("open");
		});
		
		
		$(".zoom-button").click(function(){
			wrap();
			var fileno=$(this).data("zoomno");
			console.log("zoomno :"+fileno);
			var targetImage=$("[data-no="+fileno+"]").data("url");
			console.log(targetImage); 
			$('#btn-left').attr("data-no", parseInt(fileno.substring(4, fileno.length)) -1);
			$('#btn-right').attr("data-no", parseInt(fileno.substring(4, fileno.length)) +1);
			
			$(".viewer-content > img").attr("src", targetImage); 
			
			$(".img-viewer").center();
			$(".img-viewer").fadeIn(300);
			$(".img-viewer").css("z-index","1");
		});
		
		$(".controll-viewer > a").click(function(){
			$(".img-viewer").fadeOut(300);
			$("#mask").hide();
		});
		
		$("#btn-left").click(function(){ 
			var prevIdx = $(this).attr("data-no");
			var nextIdx = $("#btn-right").attr("data-no");
			
			var changeImage="${pageContext.request.contextPath }"+list[prevIdx];
			prevIdx = (prevIdx-1) < 0 ? (list.length-1): (prevIdx-1);
			// nextIdx = nextIdx >= (list.length) ? 0 : parseInt(nextIdx)-1;
			nextIdx = ((nextIdx-1) + list.length) % list.length;
			
			//nextIdx = (nextIdx-1) % list.length;
			
			$(this).attr("data-no", prevIdx);
			$("#btn-right").attr("data-no", nextIdx);
			
			$(".viewer-content > img").attr("src", changeImage);  
		});
		
		$("#btn-right").click(function(){  
			var prevIdx = $("#btn-left").attr("data-no");
			var nextIdx = $(this).attr("data-no"); 
			var changeImage="${pageContext.request.contextPath }"+list[nextIdx];
			
			console.log(nextIdx); 
			
			prevIdx = parseInt(prevIdx)+1 > list.length-1 ? 0 : (parseInt(prevIdx)+1);
			nextIdx = (parseInt(nextIdx)+1) < list.length ? (parseInt(nextIdx)+1) : 0;
			
			console.log(prevIdx+", "+nextIdx);
			
			$("#btn-left").attr("data-no", prevIdx);
			$("#btn-right").attr("data-no", nextIdx); 
			
			$(".viewer-content > img").attr("src", changeImage);  
		});
		
		$("#width-cont").keyup(function(){
			var width = $(this).val();
			$("#image-preview").css("width", width);
		}).change(); 
		 
		$("#height-cont").keyup(function(){
			var height = $(this).val();
			$("#image-preview").css("height", height);
		}).change();
		
		
		function wrap(){
			var maskHeight=0;
			var maskWidth=0;
			
			maskHeight=$(document).height();
			maskWidth=$(document).width();
			
			$("#mask").css({
				'width':maskWidth,
				'height':maskHeight,
				'z-index':1
			});
			
			$("#mask").fadeTo("slow", 0.8);
		}
		
	});
	
	jQuery.fn.center=function(){
		/* console.log("현재 보이는 height :"+$(window).height());
		console.log("현재 보이는 width :"+$(window).width()); 
		 
		console.log("선택한 객체의 height :"+$(this).outerHeight());
		console.log("선택한 객체의 width :"+$(this).outerWidth());
		  
		console.log("현재 보이지 않는 top 영역 :"+$(window).scrollTop()); 
		console.log("현재 보이지 않는 left 영역 :"+$(window).scrollLeft()); */
		
		$(window).resize(function(){ 
			this.css("top", Math.max(0, (($(window).height()-$(this).outerHeight())/2) + $(window).scrollTop())+"px");
			this.css("left", Math.max(0, (($(window).width()-$(this).outerWidth())/2) + $(window).scrollLeft())+"px");
		});
		
		return this;
	}
	
	function readURL(input) {
    	if (input.files && input.files[0]) {
        	var reader = new FileReader();
       		reader.readAsDataURL(input.files[0]);
       		reader.onload = function(e) {
       			var width = $("#width-cont").val();
       			var height = $("#height-cont").val();
       			$("#image-preview").css({
       				"width" : width, 
       				"height" : height
       			});
          		$('#image-preview').attr('src', e.target.result);
        	}
     	}
	} 
	
	$(document).ready(function(){
	   $('#input-file').change(function() {
	        readURL(this);
	   });
	});
	
	
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="gallery">
				<div id="mask">
				</div>
				<div>
					<h1>갤러리</h1> 
					<a href="" id="upload-image">이미지 올리기</a>
				</div>
				
				<div class='img-viewer'>
					<h2>확대보기</h2>
					<div class='controll-viewer'>
						<a></a>
					</div>
				
					<a id='btn-left' data-no=""></a>
					<div class='viewer-content'>
						<img src="">
					</div>
					<a id='btn-right' data-no=""></a>
				</div>
				
				<ul style="clear: both;">
					<c:forEach items="${files }" var="files" varStatus="status">
						<li><a
							href="#"
							data-no="file${status.index }"
							data-url="${pageContext.request.contextPath }${files }"
							data-lightbox="gallery" class="image"
							style="background-image:url('${pageContext.request.contextPath }${files }')"></a>
							
							<a class="zoom-button" data-zoomno="file${status.index }" title="확대보기"></a>
							
							<a href="${pageContext.request.contextPath }/gallery/delete/1"
							class="del-button" title="삭제"></a>
						</li>
					</c:forEach>
				</ul>
			</div> 

			<div id="dialog-upload-form" title="이미지 업로드" style="display: flex;">
				<div>
					<p class="validateTips normal">이미지와 간단한 코멘트를 입력해 주세요.</p>
					<form action="${pageContext.request.contextPath }/gallery/upload" method="post" enctype="multipart/form-data">
						<label>코멘트</label>
						<input type="text" id="input-comments" name="comments" value="">
						<label>이미지</label>
						<input type="file" id="input-file" name="file">
						 
						<label>가로</label> 
						<input id="width-cont" type="number" min="10" name="width" value="200" step="10">
						<label>세로</label>
						<input id="height-cont" type="number" min="10" name="height" value="300" step="10">
						 
						<label>파일 확장자</label>
						<select>
							<option value='jpg'>==선택==</option>
							<option selected="selected" value='jpg'>jpg</option>
							<option value='png'>png</option>
							<option value='jepg'>jepg</option>
						</select>
					</form>
				</div>
				
				<div id='before-upload-image-viewer'>
					<img id='image-preview' alt="" src="${pageContext.request.contextPath }/assets/images/no_image.png">
				</div>
			</div>
			
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="gallery" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>

</html>