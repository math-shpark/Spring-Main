package com.ssafy.board.model.dao;

import java.util.HashMap;
import java.util.List;

import com.ssafy.board.model.dto.Board;

public interface BoardDAO {
	// 게시글 수정
	public void updateBoard(Board board);

	// 게시글 삭제
	public void deleteBoard(int id);

	// 게시글 추가
	public void insertBoard(Board board);

	// 게시글 조회
	public Board selectOne(int id);

	// 게시글 목록
	public List<Board> selectList(HashMap<String, String> params);
}
