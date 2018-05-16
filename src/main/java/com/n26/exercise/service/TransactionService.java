package com.n26.exercise.service;

import com.n26.exercise.model.Transaction;
import com.n26.exercise.model.TransactionStatistics;

/**
 * Service to store transaction and calculate transaction statistics
 */
public interface TransactionService {

	/**
	 * this method store transaction data if a transaction is done with in last 60 seconds 
	 */
	public boolean storeTransaction(Transaction transaction);
	
	/**
	 * this method computes and return last 60 seconds transaction statistics  
	 */
	public TransactionStatistics getTransctionStatistics();
}
