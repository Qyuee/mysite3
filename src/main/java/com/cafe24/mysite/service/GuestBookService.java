package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.GuestBookDao;
import com.cafe24.mysite.vo.GuestBookVo;

@Service
public class GuestBookService {
	@Autowired
	private GuestBookDao guestBookDao;
	
	public List<GuestBookVo> getListGuestBookVo(){
		return guestBookDao.getList();
	}
	
	public void insertGuestBookVo(GuestBookVo vo) {
		System.out.println(vo);
		guestBookDao.insert(vo); 
		System.out.println(vo);
	}
	
	public int deleteOneGuestBook(int no, String password) {
		return guestBookDao.delete(no, password);
	}
}
