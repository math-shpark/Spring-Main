package com.ssafy.mvc.model.service;

import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

	@Override
	public boolean login(String id, String pw) {
		return id.equals(pw);
	}

}
