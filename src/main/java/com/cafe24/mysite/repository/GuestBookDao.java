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
import com.cafe24.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {
	
	/**
	 * 방명록 리스트 출력
	 * @return List<GuestBookVo>
	 */
	public List<GuestBookVo> getList(){
		List<GuestBookVo> list=new ArrayList<>();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			conn=DBOpen.getConnection();
			String sql="select no, name, content, date_format(to_date, '%Y년 %m월 %d일 %H시 %i분 %s초') as to_date "
					+ "from guestbook order by no desc ";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery(); 
			
			while(rs.next()) {
				GuestBookVo vo=new GuestBookVo();
				vo.setNo(rs.getLong(1));
				vo.setName(rs.getString(2)); 
				vo.setContent(WebUtil.transfer_textarea_enter(rs.getString(3)));
				vo.setTo_date(rs.getString(4));
				
				list.add(vo);
			}
			 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			DBClose.DBclose(conn, pstmt, rs);
		}
		
		return list;
	}
	
	/**
	 * 방명록 입력
	 * @param vo
	 * @return boolean
	 */
	public boolean insert(GuestBookVo vo) {
		boolean result=false;
		
		int count=0;
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=DBOpen.getConnection();
			String sql="insert into guestbook "
					+ "values(null, ?, password(?), ?, now()) ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword()); 
			pstmt.setString(3, vo.getContent()); 
			
			count=pstmt.executeUpdate();
			
			if(count==1) {
				result=true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.DBclose(conn, pstmt);
		}
		
		
		return result;
	}
	
	/**
	 * 방명록 삭제
	 * @param no
	 * @param pass
	 * @return
	 */
	public boolean delete(int no, String pass) {
		boolean result=false;
		
		int count=0;
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=DBOpen.getConnection();
			String sql="delete from guestbook where no = ? and password= password(?) ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.setString(2, pass);
			count=pstmt.executeUpdate();
			
			if(count==1) {
				result=true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.DBclose(conn, pstmt);
		}
		
		return result;
	}
}
