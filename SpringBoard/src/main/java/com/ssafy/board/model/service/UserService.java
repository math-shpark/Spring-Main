package com.ssafy.board.model.service;

import com.ssafy.board.model.dto.User;

public interface UserService {
	
	// 회원가입
	public void join(User user, String msg) throws Exception;

	// 로그인
	public User login(String id, String pw) throws Exception;
}
