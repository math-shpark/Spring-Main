package com.ssafy.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ssafy.board.config.MyAppSqlConfig;
import com.ssafy.board.model.dao.BoardDao;
import com.ssafy.board.model.dto.Board;

@Service
public class BoardServiceImpl implements BoardService{
	private BoardDao boardDao;
	
	public BoardServiceImpl() {
		boardDao = MyAppSqlConfig.getSession().getMapper(BoardDao.class);
	}
	
//	@Autowired
	public void setBoardDao(BoardDao boardDao) {
		//직접 boardDao를 getMapper해서 boardDao에 넣어줘야됨
		this.boardDao = boardDao;
	}
	@Override
	public List<Board> getBoardList() {
		// TODO Auto-generated method stub
		System.out.println("모든 게시물을 얻어옵니다.");
		return boardDao.selectBoard();
	}

	@Override
	public Board readBoard(int id) {
		// TODO Auto-generated method stub
		System.out.println("id에 해당하는 게시물을 읽습니다.");
		boardDao.updateViewCnt(id);
		return boardDao.selectBoardById(id);
	}

	@Override
	public void writeBoard(Board board) {
		// TODO Auto-generated method stub
		System.out.println("게시물을 작성합니다.");
		boardDao.insertBoard(board);
	}

	@Override
	public void modifyBoard(Board board) {
		// TODO Auto-generated method stub
		System.out.println("게시물을 수정합니다.");
		boardDao.updateBoard(board);
	}

	@Override
	public void deleteBoard(int id) {
		// TODO Auto-generated method stub
		System.out.println("게시물을 삭제합니다.");
		boardDao.deleteBoard(id);
	}

}
