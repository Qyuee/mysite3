package com.cafe24.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MyInterceptor2 extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		// 컨트롤러가 매핑된 handler정보와 defualt handler의 정보가 있음.
		
		System.out.println("MyInterceptor2:preHandle");
		return false;
	}
	
}
