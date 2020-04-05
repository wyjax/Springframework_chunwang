package com.java.myproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.myproject.dao.LikeDAO;

@Service
public class LikeServiceImpl implements LikeService {
	
	@Autowired
	LikeDAO likedao;

	@Override
	public int LikeUp(String boardType, int writeID, String userID) throws Exception {
		return likedao.LikeUp(boardType, writeID, userID);
	}

	@Override
	public int setLikeCount(String boardType, int writeID) throws Exception {
		return likedao.setLikeCount(boardType, writeID);
	}
}
