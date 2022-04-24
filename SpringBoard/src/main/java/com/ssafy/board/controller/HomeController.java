package com.ssafy.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	// '/' 명령어를 받으면 home.jsp를 호출한다.
	@RequestMapping("/")
	public String home() {
		return "home";
	}
}
