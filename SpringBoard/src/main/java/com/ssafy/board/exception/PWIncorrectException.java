package com.ssafy.board.exception;

// 잘못된 비밀번호를 입력한 경우 처리하는 사용자 정의 예외
public class PWIncorrectException extends Exception{
	public PWIncorrectException() {
		super("비밀번호가 틀립니다.");
	}
}
