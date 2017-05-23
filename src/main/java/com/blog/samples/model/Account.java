package com.blog.samples.model;

import lombok.Getter;
import lombok.Setter;

public class Account {

	@Getter
	@Setter
	private Long accountId;
	
	@Getter
	@Setter
	private EnumAccountType accountType;	
	
	@Getter
	@Setter
	private Double balance;
	
	public Account(){}
	
	public Account(Long accountId, EnumAccountType accountType, Double balance){
		this.accountId = accountId;
		this.accountType = accountType;
		this.balance = balance;
	}
	
	public Account(EnumAccountType accountType, Double balance){
		this.accountType = accountType;
		this.balance = balance;
	}
}