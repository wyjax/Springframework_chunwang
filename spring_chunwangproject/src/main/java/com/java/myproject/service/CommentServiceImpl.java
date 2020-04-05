package com.java.myproject.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.myproject.dao.CommentDAO;
import com.java.myproject.dto.CommentDTO;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentDAO cmtdao;

	@Override
	public int commentWrite(CommentDTO cmtDAO) throws Exception {
		return cmtdao.commentWrite(cmtDAO);
	}

	@Override
	public ArrayList<CommentDTO> getList(String boardType, int idx) throws Exception {
		return cmtdao.getList(boardType, idx);
	}

	@Override
	public boolean Delete(String boardType, int commentID) throws Exception {
		return cmtdao.Delete(boardType, commentID);
	}

	@Override
	public int getCommentCount(String boardType, int writeID) throws Exception {
		return cmtdao.getCommentCount(boardType, writeID);
	}
}
