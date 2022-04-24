package com.ssafy.board.model.service;

import java.util.HashMap;
import java.util.List;

import com.ssafy.board.model.dto.Board;

public interface BoardService {
	// 글 쓰기
	public void writeBoard(Board board);

	// 글 수정
	public void modifyBoard(Board board);

	// 글 삭제
	public void deleteBoard(int id);

	// 조회 수 증가
	public void updateCnt(int id);

	// 글 얻어오기
	public Board getBoardById(int id);

	// 글 읽기
	public Board readBoard(int id);

	// 모든 게시물 조회
	public List<Board> getBoardList(HashMap<String, String> params);
}
