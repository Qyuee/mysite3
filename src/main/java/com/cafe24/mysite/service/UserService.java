package com.cafe24.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.UserDao;
import com.cafe24.mysite.vo.UserVo;

// 복잡한 비즈니스 로직이 있는 레이어.
@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public void join(UserVo vo) {
		userDao.insert(vo);
	}
	
	/*
	 * 서비스의 메소드이름은 서비스의 명확한 목적에 부합하도록 작성한다.
	 */
	public UserVo getUserEmaliAndPassword(UserVo vo) {
		return userDao.get(vo);
	}
	
}
