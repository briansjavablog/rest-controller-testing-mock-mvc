package com.blog.samples.service;

import com.blog.samples.model.Account;

public interface AccountService {
	
	public Account loadAccount(Long accountId);
	
	public Long createAccount(Account account);	
}