package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.GuestBookAjaxDao;
import com.cafe24.mysite.vo.GuestBookAjaxVo;

@Service
public class GuestBookAjaxService {
	@Autowired
	private GuestBookAjaxDao bookAjaxDao;
	
	public GuestBookAjaxVo get(Long no) {
		return bookAjaxDao.get(no); 
	}
	
	public long insert(GuestBookAjaxVo guestBookAjaxVo) {
		return bookAjaxDao.insert(guestBookAjaxVo);
	}
	
	public List<GuestBookAjaxVo> getListAjax(long _parameter) {
		return bookAjaxDao.getListAjax(_parameter);
	}
	
	public boolean deleteAjax(GuestBookAjaxVo vo) {
		if(bookAjaxDao.deleteAjax(vo) == 1) {
			return true;
		}
		return false;
	}
}
