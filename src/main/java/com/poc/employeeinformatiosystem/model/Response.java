package com.poc.employeeinformatiosystem.model;

import lombok.Data;

@Data
public class Response {
	
	private int code;
	private String message;
	private Object data;

}
