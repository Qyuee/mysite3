package com.cafe24.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.repository.BoardDao;
import com.cafe24.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired 
	private BoardDao boardDao;
	
	/**
	 * 글 목록 조회 서비스(리스트+페이징+검색)
	 * @param kwd
	 * @param nowPage
	 * @return List<BoardVo>
	 */
	public List<BoardVo> ListGet(String kwd, int nowPage){
		Map<String, Object> param=new HashMap<>();
		param.put("kwd", kwd);
		param.put("startNum", BoardVo.countList*(nowPage-1));
		param.put("endNum", BoardVo.countList);
		return boardDao.ListGet(param); 
	}
	
	/**
	 * 글 개수 조회 서비스
	 * @param kwd
	 * @return int
	 */
	public int totalCount(String kwd) { 
		return boardDao.totalCount(kwd);
	}
	
	/**
	 * 글 정보 조회 서비스
	 * @param bno
	 * @return BoardVo
	 */
	public BoardVo getOneBoard(int bno) { 
		return boardDao.getinfo(bno);
	}
	
	/**
	 * 게시글 등록 서비스
	 * @param vo
	 * @return
	 */
	public int insert(BoardVo vo) {
		vo.setContent(WebUtil.transfer_textarea_enter(vo.getContent()));
		return boardDao.insert(vo);
	}
	
	/**
	 * 글 삭제 서비스
	 * @param bno
	 * @return
	 */
	public int delete(int bno) { 
		if(boardDao.delete(bno)==1) {
			// 삭제 성공 시
		}
		return boardDao.delete(bno);
	}
	
	/**
	 * 수정 폼내의 데이터 조회 서비스
	 * @param bno
	 * @return
	 */
	public BoardVo modifyFormContent(int bno) {
		BoardVo vo=boardDao.getinfo(bno);
		vo.setContent(WebUtil.transferBrToEnter(vo.getContent()));
		return vo;
	} 
	
	/**
	 * 수정 처리 서비스
	 * @param vo
	 * @return int 
	 */
	public int modifyAction(BoardVo vo) {
		vo.setContent(WebUtil.transfer_textarea_enter(vo.getContent()));
		return boardDao.update(vo); 
	}
	
	/**
	 * 답글 등록 처리 서비스
	 * @param pBno(부모 글 번호)
	 * @param cVo(답글 정보)
	 * @return int
	 */
	public int reply(int pBno, BoardVo cVo) {
		BoardVo pVo=boardDao.getinfo(pBno);  // 부모의 vo객체 
		
		Map<String, Object> param1=new HashMap<>();
		param1.put("groupNo", pVo.getGroupNo());
		param1.put("orderNo", pVo.getOrderNo()+1);
		boardDao.reply_update(param1); // 답글이 들어갈 위치 확보
		
		Map<String, Object> param2=new HashMap<>();
		cVo.setContent(WebUtil.transfer_textarea_enter(cVo.getContent()));
		pVo.setOrderNo(pVo.getOrderNo()+1);
		pVo.setDepth(pVo.getDepth()+1);
		param2.put("pVo", pVo);
		param2.put("cVo", cVo);
		return boardDao.reply(param2); // 답글 삽입
	}
	
	/**
	 * 글 조회수 증가 서비스
	 * @param bno
	 * @return
	 */
	public int increaseCnt(int bno) { 
		return boardDao.increaseCnt(bno);
	}
	
}
