package com.n26.exercise.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.n26.exercise.model.Transaction;
import com.n26.exercise.service.TransactionService;

/**
 * Transaction handler to handle request for transactions API
 */

@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	/**
	 * This API end-point handle all incoming transaction
	 * 
	 * @return HTTP status code for successful operation
	 */
	@PostMapping(value = "/transactions")
	public ResponseEntity<?> storeTransaction(@Valid @RequestBody Transaction transaction) { 
		
		// @Valid will validate request body for null values
		
		if (transactionService.storeTransaction(transaction))
			return new ResponseEntity<>(HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

}
