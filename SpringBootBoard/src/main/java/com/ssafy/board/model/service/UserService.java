package com.ssafy.board.model.service;

import com.ssafy.board.model.dto.User;

public interface UserService {
	void join(User user, String msg) throws Exception;
	User login(String id, String pw) throws Exception;
}
