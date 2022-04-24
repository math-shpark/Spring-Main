package com.ssafy.board.exception;

// 잘못된 Id를 입력한 경우 예외를 처리하는 사용자 정의 예외
public class UserNotFoundException extends Exception {
	public UserNotFoundException() {
		super("사용자를 찾을 수 없습니다.");
	}
}	
