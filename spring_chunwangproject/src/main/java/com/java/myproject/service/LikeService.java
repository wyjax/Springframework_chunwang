package com.java.myproject.service;

public interface LikeService {
	public int LikeUp(String boardType, int writeID, String userID) throws Exception;
	
	public int setLikeCount(String boardType, int writeID) throws Exception;
}
