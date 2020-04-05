package com.java.myproject.dao;

public interface UserDAO {
	
	public int login(String userID, String userPassword) throws Exception;
	
	public int join(String userID, String userpw, String userName, String userEmail, String time) throws Exception;
	
	public int joinCheck(String userID) throws Exception;
	
	public boolean getUserEmailChecked (String userID) throws Exception;
	
	public boolean getExistEmailChecked (String userEmail) throws Exception;
	
	public String getUserEmail(String userID) throws Exception;
	
	public String isExistEmail(String userEmail) throws Exception;
	
	public String getNickname(String userID) throws Exception;
	
	public boolean setUserEmailChecked (String userID) throws Exception;
	
	public boolean getAdminCheck (String userID) throws Exception;
	
	public boolean deleteUser(String userID) throws Exception;
	
	public boolean changePw(String userID, String userPassword) throws Exception;
	
	public String isExistNickname(String nickName) throws Exception;
	
	public String getIdFind(String userName, String userEmail) throws Exception;
	
	public int changeNickName(String userID, String nickName) throws Exception;
	
	public int userDeleteAll(String userEmail) throws Exception;
}
