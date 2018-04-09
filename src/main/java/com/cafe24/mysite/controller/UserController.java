package com.cafe24.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;
import com.cafe24.security.Auth;
import com.cafe24.security.AuthUser;

// @Auth 이렇게 Controller 자체에 접근 제어를 할 수 있는데, 일단 @Auth의 타겟을 변경해야 하고, @Auth가 method에 안 붙어 있고 class에 붙어있는지에 대한 검사가 필요함.
@Controller
@RequestMapping("/user")
public class UserController {
	// 의존성 주입. - UserDao는 Root Context 생성시 컨테이너에 객체가 생성되어 있음.(톰캣 실행 시)
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(
			@ModelAttribute UserVo userVo 
			) {
		System.out.println("hello");
		return "user/joinform"; 
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Validated UserVo vo, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			/*List<ObjectError> list=bindingResult.getAllErrors();
			for(ObjectError error : list) { 
				System.out.println("Object Error : "+error);
			}
			
			model.addAllAttributes(bindingResult.getModel());*/ 
			return "user/joinform"; 
		}
		
		System.out.println(vo);
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping(value="/joinsuccess", method=RequestMethod.GET)
	public String joinsuccess() { 
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/loginform";
	}
	
	
	
	/*@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@ModelAttribute UserVo vo, Model model, HttpSession session) {
		// 세션을 다루는 방법은 HTTPSession이 있지만 기술침투이기에 다른 방법을 사용한다. -> 일단 보류
		UserVo authUser=userService.getUserEmaliAndPassword(vo);
		System.out.println(authUser); 
		if(authUser.getName() == null) { 
			model.addAttribute("result", "fail");
			return "user/loginform";
		}
		
		session.setAttribute("authUser", authUser);
		return "redirect:/main";
	}*/
	
	/*@RequestMapping("/logout")
	public String logout(HttpSession session) { 
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/main";
	}*/ 
	
	
	/*
	 * 차후 @Auth 와 인터셉터를 사용하여 접근제어를 할 것임.
	 * @Auth는 우리가 직접 설정하는 어노테이션이다.(세션과 관련한)
	 */
	@Auth 
	@RequestMapping(value="/modify", method=RequestMethod.GET) 
	public String modify(@AuthUser UserVo authUser) {
		// 접근 제어 필요
		System.out.println(authUser); 
		
		if(authUser==null) {
			return "redirect:/main";
		}
		
		return "user/modify"; 
	} 
	
	/*@ExceptionHandler( UserDaoException.class )
	public String handleUserDaoException(HttpServletRequest request, HttpServletResponse response, Exception e) {
		// 로그 남기기 
		System.out.println("UserDao 에러 발생!");
		String errors=e.getMessage();
		StringWriter errors2=new StringWriter();
		e.printStackTrace(new PrintWriter(errors2));
		
		request.setAttribute("errors", errors);
		request.setAttribute("errors2", errors2);
		return "error/exception";
	}*/
	
}
 