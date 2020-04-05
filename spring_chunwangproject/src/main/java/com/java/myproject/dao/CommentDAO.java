package com.java.myproject.dao;

import java.util.ArrayList;

import com.java.myproject.dto.CommentDTO;

public interface CommentDAO {
	
	public int commentWrite(CommentDTO cmtDAO) throws Exception;
	
	public ArrayList<CommentDTO> getList(String boardType, int idx) throws Exception;
	
	public boolean Delete(String boardType, int commentID) throws Exception;
	
	public int getCommentCount(String boardType, int writeID) throws Exception;
}
