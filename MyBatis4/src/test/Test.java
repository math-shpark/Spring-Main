package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.ssafy.mybatis.model.dto.Student;
import com.ssafy.mybatis.model.service.StudentService;

public class Test {

	public static void main(String[] args) {
//		StudentDao studentDao = new StudentDaoImpl();
//		
//		for (Student s : studentDao.selectAll())
//			System.out.println(s);

		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

		StudentService studentService = context.getBean("studentService", StudentService.class);
		
		for (Student s : studentService.getStudentList())
			System.out.println(s);
	}

}
