package com.cafe24.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor implements HandlerInterceptor{
	
	// 인터셉터 전 처리
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("MyInterceptor:preHandle");
		return true;  //false이면 인터셉터가 response 해야함. false이기에 인터셉터에서 응답이 막힘.
	} 
	
	// 컨트롤러 실행 이후
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		System.out.println("MyInterceptor:postHandle");
		// TODO Auto-generated method stub
		
	}
	
	// view까지 처리가 끝난 후.
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub 
		System.out.println("MyInterceptor:afterCompletion");
		
	}
}
