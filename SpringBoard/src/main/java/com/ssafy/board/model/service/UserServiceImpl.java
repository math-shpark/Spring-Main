package com.ssafy.board.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.board.exception.PWIncorrectException;
import com.ssafy.board.exception.UserNotFoundException;
import com.ssafy.board.model.dao.BoardDAO;
import com.ssafy.board.model.dao.UserDAO;
import com.ssafy.board.model.dto.Board;
import com.ssafy.board.model.dto.User;
import com.ssafy.board.util.SHA256;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private BoardDAO boardDAO;

	// 회원가입 메서드
	/*
	 *  회원가입 기능 안에 2개의 동작을 수행해야 하는데
	 *  하나라도 동작 수행에 예외가 발생하면 다른 기능도 수행하면 안되므로
	 *  Transactional이라는 Annotation을 통해 트랜잭션 기능을 적용한다.
	 *  Transactional 기능을 사용하기 위해 root-context.xml에서 관련 내용을 입력한다.
	 */
	@Transactional
	@Override
	public void join(User user, String msg) throws Exception {
		// 입력받은 비밀번호를 해쉬코드로 변환한 값으로 변경한다.
		user.setPw(new SHA256().getHash(user.getPw()));
		
		// 신규 가입자가 입력한 가입 메시지를 저장할 Board 객체를 하나 생성한다.
		Board b = new Board();
		
		// 생성한 Board 객체에 제목, 내용, 작성자를 입력한다.
		b.setTitle(user.getUsername() + "가입 인사입니다.");
		b.setContent(msg);
		b.setWriter(user.getUsername());
		
		// 내용 입력이 완료된 Board 객체는 글 작성 DAO 메서드를 사용하여 게시글을 추가하고
		boardDAO.insertBoard(b);
		// 회원을 추가하는 DAO 메서드를 사용하여 회원 계정을 추가한다.
		userDAO.insertUser(user);
		
		/*
		 * 위의 2개의 기능은 정상적인 회원가입이 완료되어야 이루어 질 수 있는 기능이기 때문에
		 * 둘 중 하나라도 예외가 발생하면 두 기능 모두 동작 이전의 상태로 돌아가도록(Rollback)
		 * Transactional을 이용하여 트랜잭션을 구현한다.
		 */
	}

	// 로그인 기능을 수행하는 메서드
	@Override
	public User login(String id, String pw) throws Exception {
		// 입력한 id로 해당 계정 정보를 불러온 후
		User user = userDAO.selectById(id);
		
		// 해당 id의 계정이 없으면
		if (user == null)
			// 관련 사용자 정의 예외로 예외를 던지고
			throw new UserNotFoundException();
		
		// 해당 id의 계정이 있어 객체가 생성되었지만 해당 계정의 비밀번호와 입력 값이 다르면
		if (!user.getPw().equals(new SHA256().getHash(pw)))
			// 관련 사용자 정의 예외로 예외를 던진다.
			throw new PWIncorrectException();
		else
			// 해당 id의 계정도 있고 입력한 비밀번호도 올바르면 해당 계정의 객체를 반환한다.
			return user;
	}
}
