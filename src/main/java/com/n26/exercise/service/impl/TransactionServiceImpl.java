package com.n26.exercise.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.n26.exercise.model.Transaction;
import com.n26.exercise.model.TransactionStatistics;
import com.n26.exercise.service.TransactionService;

/**
 * Implementation of <code>TransactionService</code> for storing and retrieving
 * transaction statistics details
 * 
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	/**
	 * Time complexity for both operation function is constant as we always iterate
	 * over 60 objects for getting statistics and updating/creating statistics
	 * object for new transactions, hence O(1). Space Complexity is also O(1) for
	 * overall solution
	 */

	protected final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
	private static int LAST_SECONDS = 60;
	private static int MILLISECONDS_IN_SECOND = 1000;
	/**
	 * map to store statistics for each second
	 */
	private static Map<Integer, TransactionStatistics> statisticsMap = new ConcurrentHashMap<Integer, TransactionStatistics>(
			LAST_SECONDS);

	@Override
	public boolean storeTransaction(Transaction transaction) {
		 return precomputeData(transaction);
	}

	/**
	 * compute transaction statistics and update for corresponding second
	 * 
	 * @return true or false if transaction is added in statistics
	 */
    private boolean precomputeData(Transaction transaction) {
		/* As System.currentTimeMillis returns milliseconds elapsed in UTC */
		long currentTime = System.currentTimeMillis();
		int currentSecond = Calendar.getInstance().get(Calendar.SECOND);
		logger.info(transaction + ", time of transaction : "+new Date(transaction.getTimestamp()));
		// check if transaction is done in last 60 seconds, if yes create a statistics
		// detail object for that second and store in map
		if (((currentTime - transaction.getTimestamp()) / MILLISECONDS_IN_SECOND) <= LAST_SECONDS) {
			// below compute function is added to add thread safety as traditional way of of checking if entry exist in map then update or add 
			// had a race condition.
			statisticsMap.compute(currentSecond, (key, statistics) -> {
				if (statistics == null || (currentTime - statistics.getTimestamp()) / MILLISECONDS_IN_SECOND > LAST_SECONDS) {
					statistics = new TransactionStatistics();
					statistics.setTimestamp(transaction.getTimestamp());
					statistics.setCount(1);
					statistics.setSum(transaction.getAmount());
					statistics.setMax(transaction.getAmount());
					statistics.setMin(transaction.getAmount());
					return statistics;
				}
				statistics.setCount(statistics.getCount()+1);
				statistics.setSum(statistics.getSum() + transaction.getAmount());
				statistics.setMax(Math.max(statistics.getMax(), transaction.getAmount()));
				statistics.setMin(Math.min(statistics.getMin(), transaction.getAmount()));
				
				return statistics;
			});

			return true;
		}
		return false;
	}

	public TransactionStatistics getTransctionStatistics() {
		TransactionStatistics summary = new TransactionStatistics();
		long currentTime = System.currentTimeMillis();
		// iterate over all last 60 seconds objects, include only last 60 seconds
		// entries and delete expired ones
		for (Map.Entry<Integer, TransactionStatistics> entry : statisticsMap.entrySet()) {
			TransactionStatistics statistics = entry.getValue();
			if (((currentTime - statistics.getTimestamp()) / MILLISECONDS_IN_SECOND) < LAST_SECONDS) {
				summary.setSum(summary.getSum() + statistics.getSum());
				summary.setCount(summary.getCount() + statistics.getCount());
				summary.setMax(Math.max(summary.getMax(), statistics.getMax()));
				summary.setMin(Math.min(summary.getMin(), statistics.getMin()));
			} else {
				statisticsMap.remove(entry.getKey());
			}
			if (summary.getCount() > 0) // avoid divide by zero exception
				summary.setAvg(summary.getSum() / summary.getCount());
		}
		summary.setMin(summary.getMin() == Double.MAX_VALUE ? 0.0 : summary.getMin());
		summary.setMax(summary.getMax() == Double.MIN_VALUE ? 0.0 : summary.getMax());

		logger.info("Summary Statistics  - " + summary);
		return summary;
	}
 
}
