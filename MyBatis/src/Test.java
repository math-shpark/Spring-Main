import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.ssafy.jdbc.model.dto.Student;
import com.ssafy.jdbc.model.service.StudentService;
//마이바티스-스프링 연동ver1
// 스프링에서 SqlSessionFactory를 빈으로 등록하고 기존 코드대로 Dao에 팩토리를 주입시켜 활용

//마이바티스-스프링 연동ver2
// 스프링은 객체관리전문가이기 때문에
// 쓰레드안전한 SqlSession객체를 만들어준다. 이놈을 주입받아서 사용해도 된다.

//마이바티스-스프링 연동ver3
// 매퍼주입!
public class Test {
	public static void main(String[] args) {
//		StudentDao studentDao = new StudentDaoImpl();
//		for(Student s : studentDao.selectAll())
//			System.out.println(s);
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		StudentService studentService = context.getBean("studentService", StudentService.class);
		for(Student s : studentService.getStudentList())
			System.out.println(s);
	}
}
