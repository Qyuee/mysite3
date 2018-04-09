package com.cafe24.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.dto.JSONResult;
import com.cafe24.mysite.repository.GuestBookDao;
import com.cafe24.mysite.vo.GuestBookVo;

@Controller("guestbookAPIcontroller")
@RequestMapping("/api/guestbook")
public class GuestBookController {
	
	@Autowired
	private GuestBookDao guestbookdao;
	
	@ResponseBody
	@RequestMapping("/list")
	public JSONResult list() {
		List<GuestBookVo> list=guestbookdao.getList();
		
		return JSONResult.success(list); 
	}
}
