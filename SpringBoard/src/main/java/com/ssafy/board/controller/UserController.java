package com.ssafy.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.board.model.dto.User;
import com.ssafy.board.model.service.UserService;

/*
 * /user 명령어로 입력받으면 UserController를 호출한다.
 */

@Controller
@RequestMapping("/user")
public class UserController {
	
	/*
	 * root-context.xml에 service 패키지에 있는 클래스들을 등록해둔 상태여서
	 * Autowired Annotation이 알아서 userService를 주입한다.
	 */
	@Autowired
	private UserService userService;

	// 로그인 화면으로 이동하는 메서드
	@GetMapping("login")
	public String loginForm() {
		
		// 별도의 로직 처리 없이 login.jsp로 이동한다.
		// 단, login.jsp가 user라는 하위 폴더에 있으므로 해당 경로까지 기재한다.
		return "user/login";
	}

	// 로그인 동작을 수행하는 메서드
	@PostMapping("login")
	public String login(HttpSession session, String id, String pw) throws Exception {
		
		// 입력받은 아이디와 비밀번호를 매개변수로하여 User 객체를 생성한다.
		User user = userService.login(id, pw);
		
		// userService.login에서 입력한 정보에 대한 계정이 있으면
		if (user != null)
			/*
			 *  Session에 로그인한 계정 정보를 입력하는데
			 *  username이라는 키로 user.getUsername()을 이용하여 불러온 사용자 이름 값을 저장한다. 
			 */
			session.setAttribute("username", user.getUsername());
		
		/*
		 *  로그인하여 객체가 정상적으로 생성되면 homeController를 호출하고
		 *  정상적으로 생성되지 않으면 다시 로그인을 시도하도록 login 화면을 호출하는 메서드를 호출한다.
		 *  명령의 반복 수행을 방지하기 위해 redirect 방식을 사용한다.
		 */
		
		return user != null ? "redirect:/" : "redirect:/user/login";
	}

	// 로그아웃 기능을 수행하는 메서드
	@GetMapping("logout")
	public String logout(HttpSession session) {
		// 세션에 저장된 로그인 정보를 무효화한다.
		session.invalidate();
		
		// 메인 페이지로 이동하기 위해 homeController를 호출한다.
		return "redirect:/";
	}

	// 회원가입 화면으로 이동하는 메서드
	@GetMapping("join")
	public String joinForm() {
		
		// 별도의 로직 처리 없이 join.jsp를 호출한다.
		return "user/join";
	}

	// 회원가입 기능을 수행하는 메서드
	@PostMapping("join")
	public String join(User user, String msg) {
		
		// 입력한 데이터의 문제 유무에 따라
		try {
			// 회원가입을 시도하고
			userService.join(user, msg);
		} catch (Exception e) {
			// 에러가 발생하면 회원가입 화면으로 이동하는 메서드를 호출하고
			return "redirect:/user/join";
		}
		
		// 문제없이 완료되면 로그인 화면으로 이동하는 메서드를 호출한다.
		return "redirect:/user/login";
	}
}
