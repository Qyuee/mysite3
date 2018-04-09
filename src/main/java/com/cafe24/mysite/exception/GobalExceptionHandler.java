package com.cafe24.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cafe24.dto.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GobalExceptionHandler {
	
	private static final Log LOG = LogFactory.getLog( Exception.class ); 
	
	@ExceptionHandler(Exception.class) 
	public void handlerException(
			HttpServletRequest request,
			HttpServletResponse response,
			Exception e
			) throws Exception {
		// 1. 로깅 
		
		StringWriter errors=new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		
		request.setAttribute("errors", errors.toString()); // 원래는 파일로 남기기.
		
		// 파일로 로그 남기기.
		LOG.debug("error : "+errors.toString()); 
		LOG.info("error : "+errors.toString());
		
		/* ajax에 대한 통신 오류가 있다면 web응답이 아닌 json 데이터 응답을 줘야한다. */
		String accept=request.getHeader("accept");
		
		// .* : 모든 문자
		// ajax 요청이었다는 것을 판단
		if(accept.matches(".*application/json.*")) {
			// 실패 json 응답
			JSONResult jsonResult=JSONResult.fail(errors.toString());
			
			// jackson 메세지 컨버터에게 jsonResult를 직접 보낸다.
			String json=new ObjectMapper().writeValueAsString(jsonResult);  // 어떤 데이터를 message Convertor에 맞게 변환.
			response.setContentType("application/json; charset=utf-8");
			
			// 응답 객체에 josn을 작성한다.
			response.getWriter().print(json);
		}else {
			// 2. 사과 페이지 응답 - web 응답
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
		}
		
	}
}
