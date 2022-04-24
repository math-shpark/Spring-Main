package com.ssafy.board.model.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.board.model.dao.BoardDAO;
import com.ssafy.board.model.dto.Board;

// 게시글 작업에 대한 로직 처리를 하는 Service
@Service
public class BoardServiceImpl implements BoardService {
	
	/*
	 * root-context.xml에서 mapperInterface로 bean을 등록한 상태여서
	 * spring이 알아서 BoardDAO를 주입한다.
	 */
	@Autowired
	private BoardDAO boardDAO;

	// 게시글을 작성하는 메서드
	@Override
	public void writeBoard(Board board) {
		// 게시글을 작성하는 DAO의 메서드 호출
		boardDAO.insertBoard(board);
	}

	// 게시글을 수정하는 메서드
	@Override
	public void modifyBoard(Board board) {
		/*
		 * 게시글을 수정하는 것은 기존에 작성한 글을 수정하는 방식인데
		 * modifyBoard가 매개변수로 입력받는 Board 객체는 수정한 내용이 반영된 객체이다.
		 * 하지만 글을 수정해도 기존 글을 수정하기 때문에 id는 동일하므로
		 * 매개변수로 입력받는 Board 객체의 id를 getter를 사용하여 조회한 후
		 * id에 해당하는 글을 조회하는 DAO의 메서드를 사용하여
		 * originBoard라는 변수에 기존에 작성한 글 객체를 저장한다.
		 */
		Board originBoard = boardDAO.selectOne(board.getId());
		
		/*
		 * originBoard에 저장된 기존 글 정보에서
		 * 매개변수로 입력받는 Board 객체의 title과 content를 getter를 사용하여 불러온 후
		 * 기존 글에 setter를 사용하여 불러온 수정 내용을 반영한다.
		 */
		originBoard.setTitle(board.getTitle());
		originBoard.setContent(board.getContent());
		
		// 이후 수정된 글을 다시 DB에 반영하도록 해당 DAO 메서드를 호출한다.
		boardDAO.updateBoard(originBoard);
	}

	// 게시글 삭제하는 메서드
	@Override
	public void deleteBoard(int id) {
		/*
		 * 삭제하고자 하는 글의 id를 입력받은 후
		 * id를 이용하여 글 정보를 조회하는 메서드를 실행하여
		 * 삭제하려는 글 정보를 board라는 변수에 저장한다.
		 */
		Board board = boardDAO.selectOne(id);
		
		// 불러온 글에 첨부파일이 있는 경우
		if (board.getFileUri() != null) {
			// File 클래스의 delete() 메서드를 사용하여 삭제한다.
			new File(board.getFileUri()).delete();
		}
		
		// 첨부파일 존재 여부와 상관없이 게시글을 삭제하는 메서드를 호출한다.
		boardDAO.deleteBoard(id);
	}

	// 조회 수 증가 메서드
	// 본 메서드는 controller에서 호출하지 않고 동일 클래스에 있는 readBoard 메서드에 호출된다.
	@Override
	public void updateCnt(int id) {
		// 조회 수를 증가시키려는 글 정보를 불러온다.
		Board board = boardDAO.selectOne(id);
		
		// 불러온 글 정보에 저장된 조회 수에 1을 더한 값을 다시 저장한 후
		board.setViewCnt(board.getViewCnt() + 1);
		
		// 수정사항을 반영하는 메서드를 호출한다.
		boardDAO.updateBoard(board);
	}

	// 입력한 id 값의 글 정보를 조회하는 메서드
	@Override
	public Board getBoardById(int id) {
		// 별도의 로직 처리 없이 해당 기능의 DAO 메서드 호출
		return boardDAO.selectOne(id);
	}

	// 글을 읽는 기능을 수행하는 메서드
	@Override
	public Board readBoard(int id) {
		// 위에서 작성한 updateCnt를 실행하여 조회 수를 증가한 후
		this.updateCnt(id);
		
		// 입력받은 id에 해당하는 글 정보를 반환한다.
		return boardDAO.selectOne(id);
	}

	// 게시글 목록을 조회하는 메서드
	@Override
	public List<Board> getBoardList(HashMap<String, String> params) {
		// 별도의 로직 처리 없이 해당 DAO 메서드를 통해 얻은 게시글 목록을 반환한다.
		return boardDAO.selectList(params);
	}

}
