package com.blog.samples.exception.components;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.blog.samples.exception.AccountNotFoundException;
import com.blog.samples.exception.InvalidAccountRequestException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
	
	
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	@ExceptionHandler(AccountNotFoundException.class)
	public void handleNotFound(AccountNotFoundException ex) {
		log.error("Requested account not found");
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	@ExceptionHandler(InvalidAccountRequestException.class)
	public void handleBadRequest(InvalidAccountRequestException ex) {
		log.error("Invalid account supplied in request");
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
	@ExceptionHandler(Exception.class)
	public void handleGeneralError(Exception ex) {
		log.error("An error occurred procesing request" + ex);
	}
}
