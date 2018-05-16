package com.n26.exercise.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.n26.exercise.controller.CustomDoubleSerializer;

public class TransactionStatistics {

	/*
	 * Ideally we should use BigDecimal for monetary calculation but for sake of exercise I am using 
	 * double as given in exercise 
	 * i.e. sum is a double specifying the total sum of transaction value in the last 60 seconds 
	 */
	private double sum;
	private double avg;
	private double max = Double.MIN_VALUE;
	private double min = Double.MAX_VALUE;
	private long count;
	
	/* we want to ignore time-stamp in response to Front End*/
	@JsonIgnore
	private long timestamp;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	@JsonSerialize(using = CustomDoubleSerializer.class)
	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}
	@JsonSerialize(using = CustomDoubleSerializer.class)
	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}
	@JsonSerialize(using = CustomDoubleSerializer.class)
	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}
	@JsonSerialize(using = CustomDoubleSerializer.class)
	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	@Override
	public String toString() {
		return "Total Count " + count + ", max " + max + ", avg " + avg + "," + " min " + min + ", sum " + sum;
	}
}
