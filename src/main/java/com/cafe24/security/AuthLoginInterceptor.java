package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter{
	
// @Autowired 해서 주입 받아서 사용하면 됨.
//	@Autowired
//	private UserService userService;
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println(" >> AuthLoginInterceptor:preHandler is called.");
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		
		// 지금 UserService를 통해 쿼리를 날려야 하는데, UserService가 필요함. 근데 없음. 그렇다고 new를 할 수는 없음.
		// 만약 new를 하게되면 
		UserVo vo=new UserVo(); 
		
		vo.setEmail(email);
		vo.setPassword(password);
		
		// 이러한 형태가 될 것이다. -> 이는 UserDao에 UserService가 주입되고 있는데, DI와는 관계없는 UserService를 만드는 것이다.
		
		// 2. web application context를 사용해서 필요한 객체를 뽑는다.
		// 모든 컨테이너는 ApplicationContext를 구현한다.
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()); // 컨테이너 정보를 얻어내는 구문.
		
		UserService userService = ac.getBean(UserService.class);   // 이 타입의 객체를 달라고 요청.
		UserVo authUser=userService.getUserEmaliAndPassword(vo);
		
		if(authUser==null) {
			response.sendRedirect(request.getContextPath()+"/user/login"); 
			return false;
		}
		
		HttpSession session=request.getSession(true);
		session.setAttribute("authUser", authUser);
		
		response.sendRedirect(request.getContextPath()+"/main");
		
		return false;
	}

	
	
}
