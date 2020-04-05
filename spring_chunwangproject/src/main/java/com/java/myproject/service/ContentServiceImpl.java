package com.java.myproject.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.myproject.dao.ContentDAO;
import com.java.myproject.dto.ContentDTO;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	ContentDAO cntdao;

	@Override
	public int write (String userID, String topicName, String contentName, String time, String board) throws Exception {
		return cntdao.write(userID, topicName, contentName, time, board);
	}

	@Override
	public ArrayList<ContentDTO> getList(String boardType, String searchType, String search, int pageNumber) throws Exception {
		return cntdao.getList(boardType, searchType, search, pageNumber);
	}

	@Override
	public int targetPage(int countindex, String boardType) throws Exception {
		return cntdao.targetPage(countindex, boardType);
	}

	@Override
	public ContentDTO getContent(String boardType, int idx) throws Exception {
		return cntdao.getContent(boardType, idx);
	}

	@Override
	public boolean Delete(String boardType, int writeID) throws Exception {
		return cntdao.Delete(boardType, writeID);
	}

	@Override
	public boolean SearchUp(String boardType, int writeID) throws Exception {
		return cntdao.SearchUp(boardType, writeID);
	}
}