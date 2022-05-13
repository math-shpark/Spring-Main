package com.ssafy.jdbc.model.service;

import java.util.List;

import com.ssafy.jdbc.model.dto.Student;

public interface StudentService {
	List<Student> getStudentList();
	//한 학급의 학생들을 한번에 DB에 때려박는 기능
	void registerClass(List<Student> list)  throws Exception;
}
