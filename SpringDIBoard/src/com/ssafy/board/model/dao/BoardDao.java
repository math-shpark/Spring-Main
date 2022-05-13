package com.ssafy.board.model.dao;

import java.util.List;

import com.ssafy.board.model.dto.Board;

public interface BoardDao {
	// 전체 게시물 목록 가져오기
	public List<Board> selectBoard();

	// 게시글 상세 정보 가져오기
	public Board selectBoardById(int id);

	// 게시글 등록
	public void insertBoard(Board board);

	// 조회수 증가시키기
	public void updateViewCnt(int id);

	// 게시글 삭제
	public void deleteBoard(int id);

	// 게시글 수정
	public void updateBoard(Board board);
}
