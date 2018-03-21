package com.cafe24.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.db.DBClose;
import com.cafe24.mysite.db.DBOpen;
import com.cafe24.mysite.exception.ExceptionWrapper;
import com.cafe24.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	// 글 총 갯수
	/**
	 * 검색 + 페이징 게시글 총 갯수
	 * @param kwd 
	 * @return int
	 */
	public int totalCount(String kwd) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int totalcount=0;
		
		try {
			conn=DBOpen.getConnection();
			String sql="select count(bno) from board ";
			if(kwd!=null) {
				sql=sql+" where ((title like ?) or (content like ?)) ";
			}
			
			pstmt=conn.prepareStatement(sql);
			
			if(kwd!=null) { 
				pstmt.setString(1, "%"+kwd+"%");
				pstmt.setString(2, "%"+kwd+"%");
			}
			
			rs=pstmt.executeQuery();
			
			rs.next();
			totalcount=rs.getInt(1);
		} catch (SQLException e) {
			throw new ExceptionWrapper(this.getClass().getName());
		} finally {
			DBClose.DBclose(conn, pstmt);
		} 
		
		return totalcount;
	}
	
	
	/**
	 * 리스트 + 검색 + 페이징
	 * 검색 시 키워드에 해당하는 글의 부모 및 답글을 모두 검색한다.
	 * @param kwd
	 * @param nowPage
	 * @return List<BoardVo>
	 */
	public List<BoardVo> ListGet(String kwd, int nowPage){
		List<BoardVo> list=new ArrayList<>();
		BoardVo vo=null;
		Connection conn=null;
		PreparedStatement pstmt=null; 
		ResultSet rs=null;
		
		try {
			conn=DBOpen.getConnection();
			String sql=" select bno, title, content, group_no, order_no, depth, cnt, date_format(created_date, '%Y년 %m월 %d일 %H:%i:%s'), u.no, u.name " + 
					" from board b, users u " + 
					" where (b.no=u.no) ";
			if(kwd!=null) {
				sql=sql+" and ((b.title like ?) or (b.content like ?))"+ 
						"order by group_no desc, order_no asc " + 
						"limit ?, ?";
			}else {
				sql=sql+" order by group_no desc, order_no asc "+
						" limit ?, ? ";
			}
			
			pstmt=conn.prepareStatement(sql);
			
			if(kwd!=null) {
				pstmt.setString(1, "%"+kwd+"%");
				pstmt.setString(2, "%"+kwd+"%");
				pstmt.setInt(3, BoardVo.countList*(nowPage-1)); 
				pstmt.setInt(4, BoardVo.countList);
			}else {
				pstmt.setString(1, "%"+kwd+"%");
				pstmt.setString(2, "%"+kwd+"%");
			}
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				vo=new BoardVo();
				vo.setBno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setContent(rs.getString(3)); 
				vo.setGroupNo(rs.getInt(4));
				vo.setOrderNo(rs.getInt(5));
				vo.setDepth(rs.getInt(6));
				vo.setCnt(rs.getInt(7));
				vo.setCreatedDate(rs.getString(8));
				vo.setNo(rs.getLong(9));
				vo.setUname(rs.getString(10));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			throw new ExceptionWrapper(this.getClass().getName());
		} finally {
			DBClose.DBclose(conn, pstmt, rs);
		}
		
		return list;
	}
	
	/**
	 * 조회수 증가
	 * @param bno
	 * @return
	 */
	public boolean increaseCnt(int bno) {
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=DBOpen.getConnection();
			String sql=" update board " + 
					"	set cnt=cnt+1 " + 
					"	where bno=? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			
			if(pstmt.executeUpdate()==1) {
				result=true;
			}
			
		} catch (SQLException e) {
			throw new ExceptionWrapper(this.getClass().getName());
		} finally {
			DBClose.DBclose(conn, pstmt);
		}
		
		return result;
	}
	
	public boolean reply_update(int groupNo, int orderNo) {
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=DBOpen.getConnection();
			String sql=" update board set order_no = order_no+1 "+
					" where group_no = ? and order_no >= ? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, groupNo);
			pstmt.setInt(2, orderNo);
			
			if(pstmt.executeUpdate()==1) {
				result=true;
			}
			
		} catch (SQLException e) {
			throw new ExceptionWrapper(this.getClass().getName());
		} finally {
			DBClose.DBclose(conn, pstmt);
		}
		
		return result;
	}
	
	/**
	 * 답글 달기
	 * @param pVo
	 * @param title
	 * @param content
	 * @param no
	 * @return boolean
	 */
	public boolean reply(BoardVo pVo, String title, String content, long no) {
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=DBOpen.getConnection();
			String sql=" insert into board values(null, ?, ?, ?, ?, ?, 0, now(), ?);";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, WebUtil.transfer_textarea_enter(content));
			pstmt.setInt(3, pVo.getGroupNo()); // 부모의 GroupNo 
			pstmt.setInt(4, pVo.getOrderNo()+1);
			pstmt.setInt(5, pVo.getDepth()+1);  // 부모의 DepthNo
			pstmt.setLong(6, no);  // 현재 답글쓰는 회원 번호
			
			if(pstmt.executeUpdate()==1) {
				result=true;
			}
			
		} catch (SQLException e) {
			throw new ExceptionWrapper(this.getClass().getName());
		} finally {
			DBClose.DBclose(conn, pstmt);
		}
		
		return result;
	}
	
	/**
	 * 글 수정
	 * @param bno
	 * @return boolean
	 */
	public boolean update(String title, String content, int bno) {
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=DBOpen.getConnection();
			String sql=" update board set title = ?, content = ? where bno = ? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, WebUtil.transfer_textarea_enter(content));
			pstmt.setInt(3, bno);
			
			if(pstmt.executeUpdate()==1) {
				result=true; 
			}
			
		} catch (SQLException e) {
			throw new ExceptionWrapper(this.getClass().getName());
		} finally {
			DBClose.DBclose(conn, pstmt);
		}
		
		return result;
	}
	
	/**
	 * 게시글 삭제.
	 * @param bno
	 * @return boolean
	 */
	public boolean delete(int bno) {
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=DBOpen.getConnection();
			String sql=" delete from board where bno= ? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			
			if(pstmt.executeUpdate()==1) {
				result=true;
			}
			
		} catch (SQLException e) {
			throw new ExceptionWrapper(this.getClass().getName());
		} finally {
			DBClose.DBclose(conn, pstmt);
		}
		
		return result;
	}
	
	/**
	 * 게시글 등록하기
	 * @param vo
	 * @return boolean
	 */
	public boolean insert(BoardVo vo) {
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=DBOpen.getConnection();
			String sql=" insert into board values(null, ?, ?, (select ifnull(max(group_no), 0)+1 from board b), 1, 0, 0, now(), ?);";
			pstmt=conn.prepareStatement(sql); 
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, WebUtil.transfer_textarea_enter(vo.getContent())); 
			pstmt.setLong(3, vo.getNo());
			
			if(pstmt.executeUpdate()==1) {
				result=true;
			}
			
		} catch (SQLException e) {
			throw new ExceptionWrapper(this.getClass().getName());
		} finally {
			DBClose.DBclose(conn, pstmt);
		}
		
		return result;
	}
	
	/**
	 * 하나의 글 정보 조회
	 * @param bno
	 * @return BoardVo
	 */
	public BoardVo getinfo(int bno) {
		BoardVo vo=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			conn=DBOpen.getConnection();
			String sql=" select bno, title, content, group_no, order_no, depth, cnt, date_format(created_date, '%Y년 %m월 %d일 %H:%i:%s'), u.no, u.name " + 
					" from board b, users u " + 
					" where (b.no=u.no) "+
					" and bno = ? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				vo=new BoardVo();
				vo.setBno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setContent(rs.getString(3)); 
				vo.setGroupNo(rs.getInt(4));
				vo.setOrderNo(rs.getInt(5));
				vo.setDepth(rs.getInt(6));
				vo.setCnt(rs.getInt(7));
				vo.setCreatedDate(rs.getString(8));
				vo.setNo(rs.getLong(9));
				vo.setUname(rs.getString(10));
			}
			
		} catch (SQLException e) {
			throw new ExceptionWrapper(this.getClass().getName());
		} finally {
			DBClose.DBclose(conn, pstmt, rs);
		}
		
		return vo;
	}
	
}