package com.blog.samples.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.blog.samples.exception.AccountNotFoundException;
import com.blog.samples.exception.InvalidAccountRequestException;
import com.blog.samples.model.Account;
import com.blog.samples.service.AccountService;

@RestController
public class AccountController {

	private AccountService accountService;
	
	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@RequestMapping(value = "/api/account/{accountId}", method = RequestMethod.GET)
	public Account getAccount(@PathVariable("accountId") Long accountId) {
		
		/* validate account Id parameter */
		if (accountId < 9999) {
			throw new InvalidAccountRequestException();
		}
		
		Account account = accountService.loadAccount(accountId);
		if(null==account){			
			throw new AccountNotFoundException();
		}
		
		return account;
	}
	
	@RequestMapping(value = { "/api/account" }, method = { RequestMethod.POST })
	public Account createAccount(@RequestBody Account account, 
								 HttpServletResponse httpResponse, 
								 WebRequest request) {

		Long accountId = accountService.createAccount(account);
		account.setAccountId(accountId);
		
		httpResponse.setStatus(HttpStatus.CREATED.value());
		httpResponse.setHeader("Location", String.format("%s/api/account/%s", 
										request.getContextPath(), accountId));		
		return account;
	}
}