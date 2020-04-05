package com.java.myproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.myproject.dao.UserDAO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userdao;
	
	@Override
	public int login(String userID, String userPassword) throws Exception {
		return userdao.login(userID, userPassword);
	}

	@Override
	public int join(String userID, String userpw, String userName, String userEmail, String time) throws Exception {
		return userdao.join(userID, userpw, userName, userEmail, time);
	}

	@Override
	public int joinCheck(String userID) throws Exception {
		return userdao.joinCheck(userID);
	}

	@Override
	public boolean getUserEmailChecked(String userID) throws Exception {
		return userdao.getUserEmailChecked(userID);
	}

	@Override
	public boolean getExistEmailChecked(String userEmail) throws Exception {
		return userdao.getExistEmailChecked(userEmail);
	}

	@Override
	public String getUserEmail(String userID) throws Exception {
		return userdao.getUserEmail(userID);
	}

	@Override
	public String isExistEmail(String userEmail) throws Exception {
		return userdao.isExistEmail(userEmail);
	}

	@Override
	public String getNickname(String userID) throws Exception {
		return userdao.getNickname(userID);
	}

	@Override
	public boolean setUserEmailChecked(String userID) throws Exception {
		return userdao.setUserEmailChecked(userID);
	}

	@Override
	public boolean getAdminCheck(String userID) throws Exception {
		return userdao.getAdminCheck(userID);
	}

	@Override
	public boolean deleteUser(String userID) throws Exception {
		return userdao.deleteUser(userID);
	}

	@Override
	public boolean changePw(String userID, String userPassword) throws Exception {
		return userdao.changePw(userID, userPassword);
	}

	@Override
	public String isExistNickname(String nickName) throws Exception {
		return userdao.isExistNickname(nickName);
	}

	@Override
	public String getIdFind(String userName, String userEmail) throws Exception {
		return userdao.getIdFind(userName, userEmail);
	}

	@Override
	public int changeNickName(String userID, String nickName) throws Exception {
		return userdao.changeNickName(userID, nickName);
	}

	@Override
	public int userDeleteAll(String userEmail) throws Exception {
		return userdao.userDeleteAll(userEmail);
	}
}
