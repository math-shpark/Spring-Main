package com.ssafy.mybatis.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.ssafy.mybatis.model.dto.Student;

public interface StudentDao {
	@Insert("INSERT INTO student VALUES (#{snum}, #{sname}, #{sgrade}")
	void insertStudent(Student student) throws Exception;

	@Select("SELECT FROM student WHERE snum = #{snum}")
	Student selectOne(int snum);

	@Select("SELECT * FROM student")
	List<Student> selectAll();
}
