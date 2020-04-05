package com.java.myproject.dao;

import java.util.ArrayList;

import com.java.myproject.dto.ContentDTO;

public interface ContentDAO {
	public int write (String userID, String topicName, String contentName, String time, String board) throws Exception;
	
	public ArrayList<ContentDTO> getList (String boardType, String searchType, String search, int pageNumber) throws Exception;
	
	public int targetPage(int countindex,String boardType) throws Exception;
	
	public ContentDTO getContent(String boardType, int idx) throws Exception;
	
	public boolean Delete(String boardType, int writeID) throws Exception;
	
	public boolean SearchUp(String boardType, int writeID) throws Exception;
}
