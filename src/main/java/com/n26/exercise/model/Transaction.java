package com.n26.exercise.model;

import javax.validation.constraints.NotNull;

/**
 * model class for transaction API
 */
public class Transaction {

	@NotNull
	private long timestamp;
	@NotNull
	private double amount;
	
	public Transaction() {
	}
	
	public Transaction(long timestamp, double amount) {
		this.timestamp = timestamp;
		this.amount = amount;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return " Transaction timestamp : " + timestamp + ", amount : " + amount;
	}
}
