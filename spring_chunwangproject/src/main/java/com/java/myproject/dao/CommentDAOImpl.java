package com.java.myproject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.java.myproject.dto.CommentDTO;
import com.java.myproject.util.DatabaseUtil;

@Repository
public class CommentDAOImpl implements CommentDAO {
	
	@Override
	public int commentWrite(CommentDTO cmtDAO) throws Exception {
		String sql = "INSERT INTO "+ cmtDAO.getboardType() + "_Comment VALUES (NULL, ?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cmtDAO.getWriteID());
			pstmt.setInt(2, cmtDAO.getPreComment());
			pstmt.setString(3, cmtDAO.getUserID().replaceAll("<", "&alt;").replaceAll(">", "&gt;").replaceAll("/r/n", "<br>"));
			pstmt.setString(4, cmtDAO.getCommentContent().replaceAll("<", "&alt;").replaceAll(">", "&gt;").replaceAll("/r/n", "<br>"));
			pstmt.setString(5, cmtDAO.getCommentTime().replaceAll("<", "&alt;").replaceAll(">", "&gt;").replaceAll("/r/n", "<br>"));
			
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try { if(conn != null) conn.close(); } catch(Exception e) { e.printStackTrace(); }
			try { if(pstmt != null) pstmt.close(); } catch(Exception e) { e.printStackTrace(); }
			try { if(rs != null) rs.close(); } catch(Exception e) { e.printStackTrace(); }
		}
		
		return -1;
	}
	
	@Override
	public ArrayList<CommentDTO> getList(String boardType, int idx) throws Exception {
		ArrayList<CommentDTO> cmtarr = null;
		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			sql = "SELECT * FROM " + boardType + "_Comment WHERE writeID = ?";
			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			cmtarr = new ArrayList<CommentDTO>();
			
			while(rs.next()) {
				CommentDTO cmtdto = new CommentDTO(
						rs.getInt(1),
						rs.getInt(2),
						rs.getInt(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						boardType
				);
				cmtarr.add(cmtdto);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return cmtarr;
	}
	
	@Override
	public boolean Delete(String boardType, int commentID) throws Exception {
		String sql = "DELETE FROM " + boardType + "_Comment WHERE commentID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, commentID);
			pstmt.executeUpdate();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if(conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
			try { if(pstmt != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
		}
		
		return false;
	}
	
	@Override
	public int getCommentCount(String boardType, int writeID) throws Exception {
		String sql = "SELECT COUNT(writeID) FROM " + boardType + "_Comment WHERE writeID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, writeID);
			rs = pstmt.executeQuery();
			
			if(rs.next())
				return rs.getInt(1);
			else
				return 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if(conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
			try { if(pstmt != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
		}
		
		return 0;
	}
}
