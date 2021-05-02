package com.poc.employeeinformatiosystem.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.poc.employeeinformatiosystem.constants.EmployeeInformationSystemConstants;
import com.poc.employeeinformatiosystem.domain.EmployeeInformation;
import com.poc.employeeinformatiosystem.exception.EmployeeNotFoundException;
import com.poc.employeeinformatiosystem.exception.EmployeeNotSavedException;
import com.poc.employeeinformatiosystem.model.EmployeeInfoModel;
import com.poc.employeeinformatiosystem.repository.EmployeeInformationRepository;
import com.poc.employeeinformatiosystem.service.EmployeeInformationService;

@Component
public class EmployeeInformationServiceImpl implements EmployeeInformationService {
	private static Logger logger = LoggerFactory.getLogger(EmployeeInformationServiceImpl.class);

	@Autowired
	private EmployeeInformationRepository employeeInformationRepository;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Override
	public void saveEmployeeInformation(EmployeeInformation employeeInformation) {
		logger.info("EmployeeInformationServiceImpl::saveEmployeeInformation request : {}", employeeInformation);
		EmployeeInfoModel employeeInfoModel = new EmployeeInfoModel();
		logger.info("Intiating the call to save the employee information to DB.");
		if (employeeInformation.getDateOfLeaving() == null) {
			employeeInformation.setStatus(true);
			Optional<EmployeeInformation> employeeInfo = employeeInformationRepository
					.findById(employeeInformation.getEmployeeId());
			if (employeeInfo.isEmpty()) {
				employeeInfoModel.setOperation(EmployeeInformationSystemConstants.OPERATION_NEW_EMPLOYEE);
				employeeInfoModel.setDateOfJoining(employeeInformation.getDateOfJoining());
			}
		} else {
			employeeInformation.setStatus(false);
			employeeInfoModel.setOperation(EmployeeInformationSystemConstants.OPERATION_DEACTIVATE_EMPLOYEE);
			employeeInfoModel.setDateOfLeaving(employeeInformation.getDateOfLeaving());
		}
		employeeInformationRepository.save(employeeInformation);
		logger.info("Completed the call to save the employee information to DB.");
		employeeInfoModel.setEmployeeId(employeeInformation.getEmployeeId());
		employeeInfoModel.setEmailId(employeeInformation.getEmailId());
		employeeInfoModel.setName(employeeInformation.getEmployeeName());
		employeeInfoModel.setDateOfBirth(employeeInformation.getDateOfBirth());
		if (null != employeeInfoModel.getOperation()) {
			Gson gson = new Gson();
			logger.info("Sending message with employee deatils to JMS queue: {}", employeeInfoModel);
			jmsTemplate.convertAndSend("employeeDetailsQueue", gson.toJson(employeeInfoModel));
			logger.info("Message sent successfully to JMS queue");
		}

	}

	@Override
	public EmployeeInformation getEmployeeInformation(String employeeId) throws EmployeeNotFoundException {
		logger.info("EmployeeInformationServiceImpl::getEmployeeInformation of employeeId {}", employeeId);
		Optional<EmployeeInformation> employeeInformationOptional = employeeInformationRepository.findById(employeeId);
		if (employeeInformationOptional.isPresent()) {
			return employeeInformationOptional.get();
		} else {
			logger.error(
					String.format(EmployeeInformationSystemConstants.EMPLOYEE_NOT_FOUND_EXCEPTION_MESSAGE, employeeId));
			throw new EmployeeNotFoundException(
					String.format(EmployeeInformationSystemConstants.EMPLOYEE_NOT_FOUND_EXCEPTION_MESSAGE, employeeId));
		}
	}

}
