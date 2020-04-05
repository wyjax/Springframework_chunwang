package com.java.myproject.service;

import java.util.ArrayList;

import com.java.myproject.dto.CommentDTO;

public interface CommentService {

	public int commentWrite(CommentDTO cmtDAO) throws Exception;
	
	public ArrayList<CommentDTO> getList(String boardType, int idx) throws Exception;
	
	public boolean Delete(String boardType, int commentID) throws Exception;
	
	public int getCommentCount(String boardType, int writeID) throws Exception;
}
