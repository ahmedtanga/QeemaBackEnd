package com.qeema.app.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qeema.app.dto.UsersDto;
import com.qeema.app.entity.UsersEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRestControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@Order(1)
	public void registerAPITest() throws Exception {
		// to test registerAPI you should to add new values
		mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
				.content(asJsonString(new UsersEntity(null, "AhmedTanga", "ahmedtanga1@gmail.com", "123", false)))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

	}

	@Test
	@Order(2)
	public void loginAPITest() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
				.content(asJsonString(new UsersDto("ahmedtanga1@gmail.com", "123")))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());

	}

	@Test
	@Order(3)
	public void logoutAPITest() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/user/logout?email=ahmedtanga1@gmail.com")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

	}

	@Test
	@Order(4)
	public void getTotalNumberOfUsersAPITest() throws Exception {
		mockMvc.perform(get("/user/getTotalNumberOfUsers")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("$.totalNoOfUsers").value(2))
				.andExpect(jsonPath("$.totalNoOfLoggedUsers").value(1));

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}