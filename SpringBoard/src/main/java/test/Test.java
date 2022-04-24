package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.ssafy.board.model.dto.User;
import com.ssafy.board.model.service.UserService;

public class Test {

	public static void main(String[] args) {
		ApplicationContext context = new GenericXmlApplicationContext("root-context.xml");

//		BoardDAO boardDAO = context.getBean("boardDAO", BoardDAO.class);
//		for(Board b : boardDAO.selectList())
//			System.out.println(b);

		UserService userService = context.getBean("userService", UserService.class);
		User user = new User("kimkim", "kim123", "kim");

		try {
			userService.join(user, "두 번째 가입합니다.");
		} catch (Exception e) {
			System.out.println("예외가 발생하였습니다.");
			System.out.println(e.getMessage());
		}

	}

}
