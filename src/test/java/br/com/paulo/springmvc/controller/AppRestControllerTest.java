package br.com.paulo.springmvc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.paulo.springmvc.model.Employee;
import br.com.paulo.springmvc.test.configuration.TestAppConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
@WebAppConfiguration
public class AppRestControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	private ObjectMapper mapper;

	@Before
	public void setup() {
		mockMvc = webAppContextSetup(wac).build();
		mapper = new ObjectMapper();
	}

	@Test
	public void validRequestReturns200OK() throws Exception {
		Employee employee = new Employee();

		employee.setJoiningDate(new Date());
		employee.setName("paulo");
		employee.setSalary(new BigDecimal("1000"));
		employee.setSsn("abcd");

		MockHttpServletRequestBuilder post = post("/api/employee/").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(employee));

		mockMvc.perform(post)
		.andDo(print())
		.andExpect(status().isCreated());
	}

	@Test
	public void invalidNameError() throws Exception {
		Employee employee = new Employee();

		employee.setJoiningDate(new Date());
		employee.setName("paulo");
		employee.setSalary(new BigDecimal("1000"));
		
		
		List<String> erros = new ArrayList<String>();
		erros.add("SSN não pode ser vazio");
		
		MockHttpServletRequestBuilder post = post("/api/employee/").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(employee));
		
		mockMvc.perform(post)
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(content().json(mapper.writeValueAsString(erros)));
	}
}