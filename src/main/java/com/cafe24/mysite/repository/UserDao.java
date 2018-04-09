package com.cafe24.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.db.DBClose;
import com.cafe24.mysite.exception.ExceptionWrapper;
import com.cafe24.mysite.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlsession;
	
	/**
	 * 회원 정보 수정
	 * @param vo
	 * @return boolean
	 */
	public boolean update(UserVo vo) {
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=dataSource.getConnection();
			
			String sql="update users";
			
			if(vo.getPassword()!=null && vo.getPassword()!="") {
				sql=sql+ " set name= ?, email= ?, password=password(?), gender = ? "
						+ " where no= ? ";
			}else {
				sql=sql+ " set name= ?, email= ?, gender = ? "
						+ " where no= ? ";
			}
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			if(vo.getPassword()!=null && vo.getPassword()!="") {
				pstmt.setString(3, vo.getPassword());
				pstmt.setString(4, vo.getGender());
				pstmt.setLong(5, vo.getNo());
			}else {
				pstmt.setString(3, vo.getGender());
				pstmt.setLong(4, vo.getNo());
			}
			
			if(pstmt.executeUpdate()==1) {
				result=true;
			}
			
		} catch (SQLException e) { 
			throw new ExceptionWrapper("UserDAO");
		} finally {
			DBClose.DBclose(conn, pstmt);
		}
		
		return result;
	}
	
	/**
	 * 로그인
	 * @param vo
	 * @return
	 */
	public UserVo get(UserVo vo) { 
		return sqlsession.selectOne("user.getByEmailAndPassword", vo);
		/*Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;*/
		
		/*try {
			conn=DBOpen.getConnection();
			String sql="select no, name, email, gender " + 
					" from users " +
					" where email= ? and password=password(?) ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, vo.getEmail());
			pstmt.setString(2, vo.getPassword());
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				result.setNo(rs.getLong(1));
				result.setName(rs.getString(2));  
				result.setEmail(rs.getString(3));
				result.setGender(rs.getString(4));
			}
			
		} catch (SQLException e) {
			throw new ExceptionWrapper(this.getClass().getName());   // 에러를 RuntimeException으로 전환하여 전달.
		} finally {
			DBClose.DBclose(conn, pstmt, rs);
		}*/
		
		//return result;
	}
	
	/**
	 * 회원 가입
	 * @param vo
	 * @return boolean
	 */
	public int insert(UserVo vo) {
		return sqlsession.insert("user.insert",vo);
		/*boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=dataSource.getConnection();  
			String sql="insert into users values(null, ?, ?, password(?), ?, now()); "; 
			pstmt=conn.prepareStatement(sql); 
			pstmt.setString(1, vo.getName()); 
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
			if(pstmt.executeUpdate()==1) { 
				result=true;
			} 
			 
		} catch (SQLException e) {
			throw new ExceptionWrapper(this.getClass().getName()); 
		} finally {
			DBClose.DBclose(conn, pstmt);
		}
		
		return result;*/
	}
	
	public UserVo get(String email) {
		return sqlsession.selectOne("user.getByEmail", email);
	}
	
}
