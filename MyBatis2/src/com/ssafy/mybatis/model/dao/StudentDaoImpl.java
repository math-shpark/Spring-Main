package com.ssafy.mybatis.model.dao;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.ssafy.mybatis.model.dto.Student;

public class StudentDaoImpl implements StudentDao {

	private SqlSessionFactory sqlSessionFactory;

	public StudentDaoImpl() {
		InputStream inputStream;

		try {
			String resource = "configuration.xml";
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void insertStudent(Student student) throws Exception {
		try (SqlSession session = sqlSessionFactory.openSession()) {
//			session.insert("student.insert", student);
			session.getMapper(StudentDao.class).insertStudent(student);
		}
	}

	@Override
	public Student selectOne(int snum) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
//			return session.selectOne("student.selectOne", snum);
			return session.getMapper(StudentDao.class).selectOne(snum);
		}
	}

	@Override
	public List<Student> selectAll() {
		try (SqlSession session = sqlSessionFactory.openSession()) {
//			return session.selectList("student.selectAll");
			return session.getMapper(StudentDao.class).selectAll();
		}
	}

}
