package com.kh.model.vo;

import java.sql.Date;

public class Employee {
	private int empNo; // 사번
	private String empName; // 이름
	private String job; // 직책
	private int mgr; // 직속 상사(manager)
	private Date hireDate; // java.sal.Date // 고용일
	private int sal; // 급여
	private int comm; // 커미션(인센티브)
	private int deptNo; // 부서번호
	
	public Employee() {}
	public Employee(int empNo, String empName, String job, int mgr, 
					Date hireDate, int sal, int comm, int deptNo) {
		this(empNo, empName, job, mgr, sal, comm);
		this.setHireDate(hireDate);
		this.setDeptNo(deptNo);
	}
	
	public Employee(int empNo, String empName, String job, int mgr, 
					int sal, int comm) {
		this(empNo, job, sal, comm);
		this.empName = empName;
		this.mgr = mgr;
		
	}
	
	public Employee(int empNo, String job, int sal, int comm) {
		this(job, sal, comm);
		this.setEmpNo(empNo);
	}
	
	public Employee(String job, int sal, int comm) {
		this.setJob(job);
		this.setSal(sal);
		this.setComm(comm);
	}
	
	
	public int getEmpNo() {
		return empNo;
	}
	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public int getMgr() {
		return mgr;
	}
	public void setMgr(int mgr) {
		this.mgr = mgr;
	}
	public Date getHireDate() {
		return hireDate;
	}
	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}
	public int getSal() {
		return sal;
	}
	public void setSal(int sal) {
		this.sal = sal;
	}
	public int getComm() {
		return comm;
	}
	public void setComm(int comm) {
		this.comm = comm;
	}
	public int getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}
	
	@Override
	public String toString() {
		return empNo + " / " + empName + " / " + job + " / " + mgr + " / " + 
			   hireDate + " / " + sal + " / " + comm + " / " + deptNo;
	}
	
	
}
