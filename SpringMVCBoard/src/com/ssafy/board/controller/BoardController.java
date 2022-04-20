package com.ssafy.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.board.model.dto.Board;
import com.ssafy.board.model.service.BoardService;

@Controller
public class BoardController {

	// Service 불러오기
	private BoardService boardService;

	@Autowired
	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}

	// 초기 화면
	@RequestMapping(value = "/")
	public String main() {
		return "redirect:list";
	}

	// 전체 조회
	@RequestMapping(value = "list")
	public String list(Model model) {
		List<Board> boardList = boardService.getBoardList();
		model.addAttribute("list", boardList);

		return "list";
	}

	// 게시글 상세 조회
	@RequestMapping(value = "detail")
	public String detail(Model model, int id) {
		Board board = boardService.readBoard(id);
		model.addAttribute("board", board);

		return "detail";
	}

	// 게시글 작성 화면으로 이동
	@RequestMapping(value = "writeform")
	public String writeForm() {
		return "writeform";
	}

	// 게시글 작성
	@RequestMapping("write")
	public String write(Board board) {
		boardService.writeBoard(board);

		return "redirect:detail?id=" + board.getId();
	}

	// 게시글 삭제
	@RequestMapping("delete")
	public String delete(int id) {
		boardService.deleteBoard(id);

		return "redirect:list";
	}

}
