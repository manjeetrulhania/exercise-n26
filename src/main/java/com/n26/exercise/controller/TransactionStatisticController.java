package com.n26.exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.exercise.model.TransactionStatistics;
import com.n26.exercise.service.TransactionService;

@RestController
public class TransactionStatisticController {

	@Autowired
	private TransactionService transactionService;

	/**
	 * statistics API returning last 60 seconds transactions statistics
	 */
	@GetMapping(value = "/statistics")
	public ResponseEntity<TransactionStatistics> getStatistics() {
		TransactionStatistics statistics = transactionService.getTransctionStatistics();
		ResponseEntity<TransactionStatistics> response = new ResponseEntity<TransactionStatistics>(statistics,
				HttpStatus.OK);
		return response;
	}

}
