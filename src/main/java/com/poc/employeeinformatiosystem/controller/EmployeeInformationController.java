package com.poc.employeeinformatiosystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poc.employeeinformatiosystem.domain.EmployeeInformation;
import com.poc.employeeinformatiosystem.exception.EmployeeNotFoundException;
import com.poc.employeeinformatiosystem.model.Response;
import com.poc.employeeinformatiosystem.service.EmployeeInformationService;

@RestController
public class EmployeeInformationController {

	private static Logger logger = LoggerFactory.getLogger(EmployeeInformationController.class);

	@Autowired
	private EmployeeInformationService employeeInformationService;

	@PostMapping("/saveEmployeeInfo")
	public ResponseEntity<Response> saveEmployeeInformation(@RequestBody EmployeeInformation employeeInformation) {
		logger.info("EmployeeInformationController::saveEmployeeInformation with request: {}", employeeInformation);
		Response response = new Response();
		employeeInformationService.saveEmployeeInformation(employeeInformation);

		response.setCode(HttpStatus.OK.value());
		response.setMessage("Employee information saved successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/getEmployeeInformation/{employeeId}")
	public ResponseEntity<Response> getEmployeeInformation(@PathVariable String employeeId)
			throws EmployeeNotFoundException {
		logger.info("EmployeeInformationController::getEmployeeInformation with employeeId: {}", employeeId);
		Response response = new Response();
		EmployeeInformation employeeInfo = employeeInformationService.getEmployeeInformation(employeeId);
		response.setCode(HttpStatus.OK.value());
		response.setMessage("Employee information fetched successfully");
		response.setData(employeeInfo);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
