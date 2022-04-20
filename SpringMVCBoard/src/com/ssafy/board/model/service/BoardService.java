package com.ssafy.board.model.service;

import java.util.List;

import com.ssafy.board.model.dto.Board;

public interface BoardService {
	//모든 게시물을 조회
	List<Board> getBoardList();
	//글번호에 해당하는 게시물을 읽는 기능
	Board readBoard(int id);
	
	//글하나를 작성하는 기능
	void writeBoard(Board board);
	
	//글내용을 수정하는 기능
	void modifyBoard(Board board);
	
	//글번호에 해당하는 게시물을 삭제하는 기능
	void deleteBoard(int id);
}
