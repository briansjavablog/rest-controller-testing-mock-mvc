package com.blog.samples.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.blog.samples.Application;
import com.blog.samples.model.Account;
import com.blog.samples.model.EnumAccountType;
import com.blog.samples.service.AccountService;

@RunWith(SpringRunner.class)

@SpringBootTest(webEnvironment=WebEnvironment.MOCK, classes={ Application.class })
public class AccountControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean 
	private AccountService accountServiceMock;
	
	@Before
	public void setUp() {
		 this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void should_CreateAccount_When_ValidRequest() throws Exception {
		
		when(accountServiceMock.createAccount(any(Account.class))).thenReturn(12345L);
		
		mockMvc.perform(post("/api/account")
			   .contentType(MediaType.APPLICATION_JSON)
			   .content("{ \"accountType\": \"SAVINGS\", \"balance\": 5000.0 }")						
			   .accept(MediaType.APPLICATION_JSON))
			   .andExpect(status().isCreated())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			   .andExpect(header().string("Location", "/api/account/12345"))
			   .andExpect(jsonPath("$.accountId").value("12345"))			   
			   .andExpect(jsonPath("$.accountType").value("SAVINGS"))
			   .andExpect(jsonPath("$.balance").value(5000));		
	}
	
	@Test
	public void should_GetAccount_When_ValidRequest() throws Exception {
		
		/* setup mock */
		Account account = new Account(12345L, EnumAccountType.SAVINGS, 5000.0);
		when(accountServiceMock.loadAccount(12345L)).thenReturn(account);
		
		mockMvc.perform(get("/api/account/12345")				
			   .accept(MediaType.APPLICATION_JSON))
			   .andExpect(status().isOk())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			   .andExpect(jsonPath("$.accountId").value(12345))
			   .andExpect(jsonPath("$.accountType").value("SAVINGS"))
			   .andExpect(jsonPath("$.balance").value(5000.0));
	}
	
	@Test
	public void should_Return404_When_AccountNotFound() throws Exception {
		
		/* setup mock */
		when(accountServiceMock.loadAccount(12345L)).thenReturn(null);
		
		mockMvc.perform(get("/api/account/12345")				
			   .accept(MediaType.APPLICATION_JSON))
			   .andExpect(status().isNotFound());
	}
	
}