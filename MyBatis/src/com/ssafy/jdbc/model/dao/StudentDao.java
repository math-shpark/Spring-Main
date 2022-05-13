package com.ssafy.jdbc.model.dao;

import java.util.List;

import com.ssafy.jdbc.model.dto.Student;

public interface StudentDao {
	void insertStudent(Student student) throws Exception;
	Student selectOne(int snum);
	List<Student> selectAll();
}
