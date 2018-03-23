package com.cafe24.mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession; 
	
	// 글 총 갯수
	/**
	 * 검색 + 페이징 게시글 총 갯수
	 * @param kwd 
	 * @return int
	 */
	public int totalCount(String kwd) {
		return sqlSession.selectOne("board.totalCount", kwd);
	}
	
	/**
	 * 리스트 + 검색 + 페이징
	 * 검색 시 키워드에 해당하는 글의 부모 및 답글을 모두 검색한다.
	 * @param param
	 * @return List<BoardVo>
	 */
	public List<BoardVo> ListGet(Map<String, Object> param){
		return sqlSession.selectList("board.listGet", param);
	}
	
	/**
	 * 조회수 증가
	 * @param bno
	 * @return int
	 */
	public int increaseCnt(int bno) {
		return sqlSession.update("board.increaseCnt", bno);
	}
	
	/**
	 * 답글 위치 조정
	 * @param param
	 * @return int
	 */
	public int reply_update(Map<String, Object> param) {
		return sqlSession.update("board.reply_update", param);
	}
	
	/**
	 * 답글 달기
	 * @param param
	 * @return int
	 */
	public int reply(Map<String, Object> param) {
		return sqlSession.insert("board.reply", param);
	}
	
	/**
	 * 글 수정
	 * @param bno
	 * @return int
	 */
	public int update(BoardVo vo) {
		return sqlSession.update("board.modify", vo);
	}
	
	/**
	 * 게시글 삭제.
	 * @param bno
	 * @return int
	 */
	public int delete(int bno) { 
		return sqlSession.delete("board.delete", bno); 
	}
	
	/**
	 * 게시글 등록하기
	 * @param vo
	 * @return int
	 */
	public int insert(BoardVo vo) {
		return sqlSession.insert("board.insert", vo); 
	}
	
	/**
	 * 하나의 글 정보 조회
	 * @param bno
	 * @return BoardVo 
	 */
	public BoardVo getinfo(int bno) {
		return sqlSession.selectOne("board.getInfo", bno);
	}
	
}