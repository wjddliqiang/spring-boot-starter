package com.lqq.bookbar.controller;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {

	@Autowired
	private MockMvc mockmvc;
	
	@MockBean
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() throws Exception {
		//Mockito.when(jdbcTemplate.queryForObject("", new RowMapper<T>,"")).thenReturn();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLogin() {
		fail("Not yet implemented");
	}

}
