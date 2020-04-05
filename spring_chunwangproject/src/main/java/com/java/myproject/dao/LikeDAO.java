package com.java.myproject.dao;

public interface LikeDAO {
	public int LikeUp(String boardType, int writeID, String userID) throws Exception;
	
	public int setLikeCount(String boardType, int writeID) throws Exception;
}
