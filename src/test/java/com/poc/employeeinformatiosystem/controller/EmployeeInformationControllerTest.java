package com.poc.employeeinformatiosystem.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;
import com.poc.employeeinformatiosystem.domain.EmployeeInformation;
import com.poc.employeeinformatiosystem.service.EmployeeInformationService;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class EmployeeInformationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeInformationService employeeInformationService;

	@Test
	void saveEmployeeInformationTest() throws Exception {
		Gson gson = new Gson();
		EmployeeInformation employeeInformation = new EmployeeInformation();
		employeeInformation.setEmployeeId("E1IN00000");

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/saveEmployeeInfo").content(gson.toJson(employeeInformation))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	@Test
	void getEmployeeInformationTest() throws Exception {
		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/getEmployeeInformation/E1IN00000").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
