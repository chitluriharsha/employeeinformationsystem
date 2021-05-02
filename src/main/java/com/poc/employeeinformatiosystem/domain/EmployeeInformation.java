package com.poc.employeeinformatiosystem.domain;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class EmployeeInformation{
	@Id
	private String employeeId;
	private String employeeName;
	private Date dateOfBirth;
	private String designation;
	private Date dateOfJoining;
	private String workLocation;
	private String contactNo;
	private String emailId;
	private Date dateOfLeaving;
	private Boolean status;
}
