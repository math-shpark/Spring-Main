package test;

import com.ssafy.mybatis.model.dao.StudentDao;
import com.ssafy.mybatis.model.dao.StudentDaoImpl;
import com.ssafy.mybatis.model.dto.Student;

public class Test {

	public static void main(String[] args) {
		StudentDao studentDao = new StudentDaoImpl();
		
		for (Student s : studentDao.selectAll())
			System.out.println(s);
	}
	
}
