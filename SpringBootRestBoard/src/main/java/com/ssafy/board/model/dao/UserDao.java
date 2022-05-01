package com.ssafy.board.model.dao;

import com.ssafy.board.model.dto.User;

public interface UserDao{
	void insertUser(User user) throws Exception;
	User selectById(String id) throws Exception;
}