package com.ssafy.jdbc.model.dto;

public class Student {
	private int snum;
	private String sname;
	private int sgrade;
	public Student() {
		
	}
	
	public Student(int snum, String sname, int sgrade) {
		super();
		this.snum = snum;
		this.sname = sname;
		this.sgrade = sgrade;
	}

	public int getSnum() {
		return snum;
	}
	public void setSnum(int snum) {
		this.snum = snum;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public int getSgrade() {
		return sgrade;
	}
	public void setSgrade(int sgrade) {
		this.sgrade = sgrade;
	}
	@Override
	public String toString() {
		return "Student [snum=" + snum + ", sname=" + sname + ", sgrade=" + sgrade + "]";
	}
	
}
