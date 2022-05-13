package com.ssafy.mybatis.model.dao;

import java.util.List;

import com.ssafy.mybatis.model.dto.Student;

public interface StudentDao {
	void insertStudent(Student student) throws Exception;

	Student selectOne(int snum);

	List<Student> selectAll();
}
