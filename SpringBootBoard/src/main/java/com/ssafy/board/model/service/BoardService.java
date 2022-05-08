package com.ssafy.board.model.service;

import java.util.HashMap;
import java.util.List;

import com.ssafy.board.model.dto.Board;

public interface BoardService {
	//글쓰기
	void writeBoard(Board board);
	//글수정
	void modifyBoard(Board board);
	//글삭제
	void deleteBoard(int id);
	//조회수 증가
	void updateCnt(int id);
	//글 얻어오기
	Board getBoardById(int id);
	//글 읽기
	Board readBoard(int id);
	//모든게시물 조회
	List<Board> getBoardList(HashMap<String, String> params);
}
