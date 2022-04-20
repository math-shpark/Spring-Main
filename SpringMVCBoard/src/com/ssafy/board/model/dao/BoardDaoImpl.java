package com.ssafy.board.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ssafy.board.model.dto.Board;

//@Component
@Repository
public class BoardDaoImpl implements BoardDao{

	@Override
	public List<Board> selectBoard() {
		// TODO Auto-generated method stub
		System.out.println("BoardDao selectBoard");
		return null;
	}

	@Override
	public Board selectBoardById(int id) {
		// TODO Auto-generated method stub
		System.out.println("BoardDao selectBoardById");
		return null;
	}

	@Override
	public void insertBoard(Board board) {
		// TODO Auto-generated method stub
		System.out.println("BoardDao insertBoard");
	}

	@Override
	public void updateViewCnt(int id) {
		// TODO Auto-generated method stub
		System.out.println("BoardDao updateViewCnt");
		
	}

	@Override
	public void deleteBoard(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBoard(Board board) {
		// TODO Auto-generated method stub
		
	}

}
