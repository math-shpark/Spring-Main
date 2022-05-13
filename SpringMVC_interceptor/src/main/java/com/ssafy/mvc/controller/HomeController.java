package com.ssafy.mvc.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssafy.mvc.model.service.LoginService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@GetMapping("login")
	public String login() {
		return "login";
	}

	@PostMapping("login")
	public String doLogin(HttpSession session, String id, String pw) {
		boolean result = loginService.login(id, pw);

		if (result) {
			session.setAttribute("id", id);
			return "redirect:/";
		} else {
			return "redirect:/login";
		}
	}

	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("id");

		return "redirect:/";
	}
	
	@GetMapping("a")
	public String a() {
		
		return "a";
	}

}
