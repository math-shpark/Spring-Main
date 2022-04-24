package com.ssafy.board.model.dao;

import com.ssafy.board.model.dto.User;

public interface UserDAO {
	
	// 사용자 추가
	public void insertUser(User user) throws Exception;

	// 아이디 검색
	public User selectById(String id) throws Exception;
}