package com.cafe24.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao { 
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	/**
	 * 방명록 리스트 출력
	 * @return List<GuestBookVo>
	 */
	public List<GuestBookVo> getList(){
		return sqlSession.selectList("guestbook.getList");
		/*List<GuestBookVo> list=new ArrayList<>();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=dataSource.getConnection();
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
		
		return list;*/
	}
	
	/**
	 * 방명록 입력
	 * @param vo
	 * @return boolean
	 */
	public int insert(GuestBookVo vo) {
		int count = sqlSession.insert("guestbook.insert", vo); 
		
		return count; 
		/*boolean result=false;
		
		int count=0;
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=dataSource.getConnection();
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
		
		
		return result;*/
	}
	
	/**
	 * 방명록 삭제
	 * @param no
	 * @param pass
	 * @return
	 */
	public int delete(int no, String pass) {
		Map<String, Object> map=new HashMap<>();
		map.put("no", no); 
		map.put("pwd", pass);
		return sqlSession.delete("guestbook.delete", map); 
		/*boolean result=false;
		int count=0;
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=dataSource.getConnection();
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
		
		return result;*/
	}
}
