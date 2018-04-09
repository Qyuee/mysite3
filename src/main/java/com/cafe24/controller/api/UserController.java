package com.cafe24.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.dto.JSONResult;
import com.cafe24.mysite.repository.UserDao;
import com.cafe24.mysite.vo.UserVo;

@Controller("userAPIController") // bean 등록 시 bean id가 중복되서 따로 지정함.
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserDao userdao;
	
	@ResponseBody
	@RequestMapping("checkemail")
	public JSONResult checkMail(@RequestParam(value = "email", required = true, defaultValue = "") String email) {
		
		//ex) 실패 시.
		UserVo vo=new UserVo();
		vo=userdao.get(email);
		return JSONResult.success(vo==null ? "not exist" : "exist");
	}
	
}
