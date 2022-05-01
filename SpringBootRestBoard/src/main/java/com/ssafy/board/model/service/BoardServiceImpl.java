package com.ssafy.board.model.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.board.model.dao.BoardDao;
import com.ssafy.board.model.dto.Board;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDao boardDao;
	
	@Override
	public void writeBoard(Board board) {
		// TODO Auto-generated method stub
		boardDao.insertBoard(board);
	}

	@Override
	public void modifyBoard(Board board) {
		// TODO Auto-generated method stub
		Board originBoard = boardDao.selectOne(board.getId());
		originBoard.setTitle(board.getTitle());
		originBoard.setContent(board.getContent());
		boardDao.updateBoard(originBoard);
	}

	@Override
	public void deleteBoard(int id) {
		// TODO Auto-generated method stub
		Board board = boardDao.selectOne(id);
		if( board.getFileUri() != null ) {
			new File(board.getFileUri()).delete();
		}
		boardDao.deleteBoard(id);
	}

	@Override
	public void updateCnt(int id) {
		// TODO Auto-generated method stub
		Board board = boardDao.selectOne(id);
		board.setViewCnt(board.getViewCnt() + 1);
		boardDao.updateBoard(board);
	}

	@Override
	public Board getBoardById(int id) {
		// TODO Auto-generated method stub
		return boardDao.selectOne(id);
	}

	@Override
	public Board readBoard(int id) {
		// TODO Auto-generated method stub
		this.updateCnt(id);
		return boardDao.selectOne(id);
	}

	@Override
	public List<Board> getBoardList(HashMap<String, String> params) {
		// TODO Auto-generated method stub
		return boardDao.selectList(params);
	}

}
