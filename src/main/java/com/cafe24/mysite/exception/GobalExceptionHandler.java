package com.cafe24.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public void handlerException(
			HttpServletRequest request,
			HttpServletResponse response,
			Exception e
			) throws Exception {
		// 1. 로깅 
		StringWriter errors=new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		
		request.setAttribute("errors", errors); // 원래는 파일로 남기기.
		// 2. 사과
		
		request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
		
	}
}
