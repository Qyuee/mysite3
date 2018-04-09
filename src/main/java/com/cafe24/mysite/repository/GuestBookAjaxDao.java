package com.cafe24.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.GuestBookAjaxVo;

@Repository
public class GuestBookAjaxDao {
	@Autowired
	private SqlSession sqlSession;
	
	public GuestBookAjaxVo get(Long no) {
		return sqlSession.selectOne("guestbookAjax.getByNo", no);
	}
	
	public long insert(GuestBookAjaxVo guestBookAjaxVo) {
		return sqlSession.insert("guestbookAjax.insertGuestBookAjax", guestBookAjaxVo);
	}
	
	public List<GuestBookAjaxVo> getListAjax(long _parameter) { 
		return sqlSession.selectList("guestbookAjax.getListAjax", _parameter);
	}
	
	public int deleteAjax(GuestBookAjaxVo vo) {
		return sqlSession.delete("guestbookAjax.deleteAjax", vo); 
	}
}
