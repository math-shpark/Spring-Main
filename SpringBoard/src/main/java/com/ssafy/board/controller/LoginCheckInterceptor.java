package com.ssafy.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);

	/*
	 * 모든 요청이 LoginCheckInterceptor를 거치는 것이 아니라
	 * servlet-context에서 등록한 interceptor 태그에서 입력한 명령만 해당한다.  
	 */
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// Session 객체를 호출해서
		HttpSession session = request.getSession();
		
		/*
		 *  session에 username이라는 이름의 속성 값이 있는지 확인해서
		 *	로그인이 되어 있으면 username이란 키의 값으로 로그인한 사용자의 이름이 저장되어 있고
		 *	로그인이 되어 있지 않으면 null
		 */
		
		// 로그인이 되어 있지 않은 상태면
		if (session.getAttribute("username") == null) {
			// 로그에 해당 메시지를 출력하게 하고
			logger.debug("로그인안됨");
			// 로그인 페이지로 이동하게 한 후
			response.sendRedirect(request.getContextPath() + "/user/login");
			// 요청이 처리되지 않도록 false 반환
			return false;
		}
		
		// 문제가 없으면 계속해서 요청을 처리하도록 true 반환
		return true;
	}

}
