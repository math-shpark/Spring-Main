package com.ssafy.board;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.ssafy.board.model.dto.Board;
import com.ssafy.board.model.service.BoardService;

public class Test {
	public static void main(String[] args) {
		//applicationContext 빌드
		ApplicationContext context
		= new GenericXmlApplicationContext("applicationContext.xml");
		//필요한 객체 회수
		BoardService boardService
		= context.getBean("boardServiceImpl", BoardService.class);
		//사용
		for(Board b : boardService.getBoardList()) {
			System.out.println(b);
		}
	}
}
