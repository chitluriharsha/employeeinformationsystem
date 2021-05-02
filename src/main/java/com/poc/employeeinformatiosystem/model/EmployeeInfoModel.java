package com.poc.employeeinformatiosystem.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;

@Data
public class EmployeeInfoModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String employeeId;
	private Date dateOfJoining;
	private Date dateOfLeaving;
	private Boolean status;
	private String operation;
	private String emailId;
	private String name;
	private Date dateOfBirth;

}
