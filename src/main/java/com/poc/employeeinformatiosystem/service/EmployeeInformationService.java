package com.poc.employeeinformatiosystem.service;

import com.poc.employeeinformatiosystem.domain.EmployeeInformation;
import com.poc.employeeinformatiosystem.exception.EmployeeNotFoundException;
import com.poc.employeeinformatiosystem.exception.EmployeeNotSavedException;

public interface EmployeeInformationService {

	public void saveEmployeeInformation(EmployeeInformation employeeInformation);

	public EmployeeInformation getEmployeeInformation(String employeeId) throws EmployeeNotFoundException;

}
