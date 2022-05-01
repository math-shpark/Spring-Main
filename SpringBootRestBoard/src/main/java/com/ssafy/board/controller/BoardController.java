package com.ssafy.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

	@GetMapping("list")
	public String list() {
		return "board/list";
	}

	@GetMapping("writeForm")
	public String writeForm() {
		return "board/writeform";
	}

	@GetMapping("detail")
	public String detail() {
		return "board/detail";
	}

	@GetMapping("updateForm")
	public String updateForm() {
		return "board/updateform";
	}
}
