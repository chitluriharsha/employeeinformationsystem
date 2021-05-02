package com.poc.employeeinformatiosystem.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.poc.employeeinformatiosystem.model.Response;

@ControllerAdvice
public class EmployeeInformationSystemExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(EmployeeInformationSystemExceptionHandler.class);	

	@ExceptionHandler(value = { EmployeeNotFoundException.class, EmployeeNotSavedException.class })
	protected ResponseEntity<Response> handleException(Exception e) {
		logger.error(e.getMessage(), e);
		Response response = new Response();
		response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setMessage(e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<Response> handleGenericException(Exception e) {
		logger.error(e.getMessage(), e);
		Response response = new Response();
		response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setMessage("Generic exception found");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
