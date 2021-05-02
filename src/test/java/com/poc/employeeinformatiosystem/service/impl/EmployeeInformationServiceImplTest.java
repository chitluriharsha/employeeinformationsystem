package com.poc.employeeinformatiosystem.service.impl;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.poc.employeeinformatiosystem.domain.EmployeeInformation;
import com.poc.employeeinformatiosystem.exception.EmployeeNotFoundException;
import com.poc.employeeinformatiosystem.repository.EmployeeInformationRepository;

@ExtendWith(SpringExtension.class)
class EmployeeInformationServiceImplTest {

	@InjectMocks
	private EmployeeInformationServiceImpl employeeInformationServiceImpl;

	@Mock
	private EmployeeInformationRepository employeeInformationRepository;
	
	@Mock
	private JmsTemplate jmsTemplate;

	@Test
	void getEmployeeInformationTest() throws EmployeeNotFoundException {
		Optional<EmployeeInformation> employeeInformationOptional = Optional.ofNullable(new EmployeeInformation());
		employeeInformationOptional.get().setEmployeeId("E1IN00000");
		when(employeeInformationRepository.findById(ArgumentMatchers.anyString())).thenReturn(employeeInformationOptional);
		EmployeeInformation result = employeeInformationServiceImpl.getEmployeeInformation("E1IN00000");
		Assertions.assertEquals("E1IN00000", result.getEmployeeId());
	}
	
	@Test
	void getEmployeeInformationExceptionTest() throws EmployeeNotFoundException {
		Optional<EmployeeInformation> employeeInformationOptional = Optional.empty();
		when(employeeInformationRepository.findById(ArgumentMatchers.anyString())).thenReturn(employeeInformationOptional);
		Assertions.assertThrows(EmployeeNotFoundException.class, () -> employeeInformationServiceImpl.getEmployeeInformation("E1IN00000"));
	}
	
	@Test
	void saveEmployeeInformationTest() {
		EmployeeInformation employeeInformation = new EmployeeInformation();
		employeeInformation.setEmployeeId("E1IN00000");
		Optional<EmployeeInformation> employeeInformationOptional = Optional.ofNullable(new EmployeeInformation());
		employeeInformationOptional.get().setEmployeeId("E1IN00000");
		when(employeeInformationRepository.findById(ArgumentMatchers.anyString())).thenReturn(employeeInformationOptional);
		employeeInformationServiceImpl.saveEmployeeInformation(employeeInformation);	
	}
	
	@Test
	void saveEmployeeInformationTestNewEmployee() {
		EmployeeInformation employeeInformation = new EmployeeInformation();
		employeeInformation.setEmployeeId("E1IN00000");
		Optional<EmployeeInformation> employeeInformationOptional = Optional.empty();
		when(employeeInformationRepository.findById(ArgumentMatchers.anyString())).thenReturn(employeeInformationOptional);
		doNothing().when(jmsTemplate).convertAndSend(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
		employeeInformationServiceImpl.saveEmployeeInformation(employeeInformation);	
	}
	@Test
	void saveEmployeeInformationTestDeactivateEmployee() {
		EmployeeInformation employeeInformation = new EmployeeInformation();
		employeeInformation.setEmployeeId("E1IN00000");
		employeeInformation.setDateOfLeaving(new Date(new java.util.Date().getTime()));
		Optional<EmployeeInformation> employeeInformationOptional = Optional.empty();
		when(employeeInformationRepository.findById(ArgumentMatchers.anyString())).thenReturn(employeeInformationOptional);
		doNothing().when(jmsTemplate).convertAndSend(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
		employeeInformationServiceImpl.saveEmployeeInformation(employeeInformation);	
	}
}
