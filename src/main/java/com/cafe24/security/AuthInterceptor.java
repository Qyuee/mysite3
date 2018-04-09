package com.cafe24.security;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.mysite.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 1. 핸들러 종류 확인. Default Handler? Dispatcher Servlet Handler ?
		// 만약 HandlerMethod 즉, 컨트롤러와 HandlerMapping 객체와의 매핑 결과인 HandlerMethod가 아니라면 정적 자원으로 인지한다.
		if(handler instanceof HandlerMethod == false) {
			return false;
		} 
		  
		//HandlerMapping handlerMapping=(HandlerMapping)WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean(requiredType);
		//System.out.println(request.getServletContext().getServletContextName());  // mysite3
		
		/*ServletContext context= WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getServletContext();
		System.out.println(context.getServerInfo());
		System.out.println(context.getServletContextName());
		
		System.out.println("==================================================================================================");
		
		String[] beanNames=WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBeanDefinitionNames();
		
		for(int i=0; i<beanNames.length; i++) { 
			System.out.println(beanNames[i]);
		}
		
		System.out.println("====================================================================================================");
		WebApplicationContext ac= WebApplicationContextUtils.getWebApplicationContext(request.getServletContext(), FrameworkServlet.SERVLET_CONTEXT_PREFIX+"mysite3");
		System.out.println(ac); 
		System.out.println(ac.getApplicationName());
		
		String [] beanNamesAC=ac.getBeanDefinitionNames();
		
		for(int i=0; i<beanNamesAC.length; i++) {
			System.out.println(beanNamesAC[i]);
		}
		
		ServletContext servletContext=request.getServletContext();
		Enumeration<String> enumeration = servletContext.getAttributeNames();
		
		while(enumeration.hasMoreElements()) {
			System.out.println(enumeration.nextElement()); 
		}
		  
		WebApplicationContext ac= WebApplicationContextUtils.getWebApplicationContext(request.getServletContext(), FrameworkServlet.SERVLET_CONTEXT_PREFIX+"spring2");
		System.out.println(ac); 
		
		String [] beanNamesAC=ac.getBeanDefinitionNames(); 
		System.out.println("\n\n=========================================================================================================");
		for(int i=0; i<beanNamesAC.length; i++) {
			System.out.println(beanNamesAC[i]);
		}
		
		System.out.println("=========================================================================================================\n\n");
		
		RequestMappingHandlerMapping handlerMapping = ac.getBean(RequestMappingHandlerMapping.class);
		
		System.out.println(handlerMapping); 
		
		Map<RequestMappingInfo, HandlerMethod> urlMap=handlerMapping.getHandlerMethods();
		System.out.println(" >> "+urlMap.isEmpty());
		Set<RequestMappingInfo> keyset = urlMap.keySet();
		Iterator<RequestMappingInfo> it = keyset.iterator(); 
		
		while(it.hasNext()) {
			RequestMappingInfo req = it.next();
			System.out.println("key : "+req.toString() +"   /"+urlMap.get(req).getBeanType().getTypeName());
			System.out.println(urlMap.get(req).getMethod());
		}
		
		System.out.println("--------------------------------------------------------------------------------------");
		SimpleUrlHandlerMapping beanNameUrlHandlerMapping = ac.getBean(SimpleUrlHandlerMapping.class);
		 
		Map<String, Object> map=beanNameUrlHandlerMapping.getHandlerMap(); 
		
		Set<String> keyset2 = map.keySet();
		Iterator<String> it2 = keyset2.iterator(); 
		while(it2.hasNext()) {  
			String key = (String) it2.next();
			System.out.println("key : "+key+"   /"+map.get(key));
		}
		
		System.out.println("--------------------------------------------------------------------------------------");*/
		
		/*Map<String, Object> urlMap= (HashMap<String, Object>)handlerMapping.getUrlMap();
		
		System.out.println(urlMap);
		
		Set<String> keyset = urlMap.keySet();
		Iterator it = keyset.iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			System.out.println("key : "+key+"   /"+urlMap.get(key));
		}*/
		
		// 2. Casting 
		HandlerMethod handlerMethod= (HandlerMethod)handler;
		System.out.println("=====================[AuthInterceptor]=======================");
		Method method = handlerMethod.getMethod(); 
		System.out.println("메소드 이름 : "+method.getName());
		Parameter[] parameters = method.getParameters(); 
		
		// Handler Method의 파라미터 타입 정보
		for(int i=0; i<parameters.length; i++) {
			System.out.println(parameters[i].toString());   
		}
		
		System.out.println("handlerMethod.getBean() : "+handlerMethod.getBean());
		System.out.println("handlerMethod.getBeanType() : "+handlerMethod.getBeanType());
		System.out.println("handlerMethod.getMethodParameters() : "+handlerMethod.getMethodParameters());
		MethodParameter methodParameter= handlerMethod.getReturnType();
		System.out.println(methodParameter.hasParameterAnnotations()); 
		
		System.out.println("===============================================================");
		
		
		
		
		// 3. @Auth를 뽑아내기.
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class); // Auth 인터페이스가 나옴.
		
		// 4. Method에 @Auth가 없는 경우
		if(auth==null) {
			return true;
		}
		
		// 5. @Auth가 붙어 있는 경우, 인증여부 체크
		System.out.println(" >> "+auth.role());
		HttpSession session=request.getSession(true);
		
		if(session==null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		UserVo authUser=(UserVo)session.getAttribute("authUser");
		
		if(authUser==null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		// 6. 접근 허가.
		return true;
	}
}
