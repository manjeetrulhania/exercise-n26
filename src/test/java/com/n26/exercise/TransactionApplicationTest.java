package com.n26.exercise;

import static org.hamcrest.CoreMatchers.is;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.exercise.model.Transaction;
import com.n26.exercise.model.TransactionStatistics;

/**
 * Integration Testing of Transaction API
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class TransactionApplicationTest {
	private static String url = getLocalUrl();

	@Autowired
	private TestRestTemplate restTemplate;

	/**
	 * test case for storing a transaction
	 */
	@Test
	public void storeTransaction() {
		Random random = new Random();
		ResponseEntity<?> response = storeTransaction((System.currentTimeMillis() - random.nextInt(1000)), 10);
		/** validate if http response status code is 201 or not */
		Assert.assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
	}

	/**
	 * test case for /statistics API
	 */
	@Test
	public void getTransactionStatistics() throws Exception {
		/** store a transaction */
		Random random = new Random();
		Thread.sleep(2000); // sleep for 2 seconds  to invalidate above transaction in storeTransaction test case
		storeTransaction((System.currentTimeMillis() - random.nextInt(40000)), 10);
		storeTransaction((System.currentTimeMillis() - random.nextInt(30000)), 20);
		storeTransaction((System.currentTimeMillis() - random.nextInt(20000)), 30);

		TransactionStatistics statistics = this.restTemplate.getForObject(url + "/statistics",
				TransactionStatistics.class);
		/** validate calculations */
		Assert.assertThat(statistics.getMax(), is(30.0));
		Assert.assertThat(statistics.getSum(), is(60.0));
		Assert.assertThat(statistics.getMin(), is(10.0));
		Assert.assertThat(statistics.getAvg(), is(20.0));
		Assert.assertThat(statistics.getCount(), is(3l));

	}


	
	private ResponseEntity<?> storeTransaction(long timestamp, double amount) {
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setTimestamp(timestamp);

		HttpEntity<Object> request = new HttpEntity<Object>(transaction, getApiHeaders());
		return restTemplate.exchange(url + "/transactions", HttpMethod.POST, request, ResponseEntity.class);
	}

	/** setting headers as required */
	private HttpHeaders getApiHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return headers;
	}

	/** build local url */
	private static String getLocalUrl() {
		String port = System.getProperty("server.port") == null ? "8080" : System.getProperty("server.port");
		String path = System.getProperty("server.contextPath") == null ? "" : System.getProperty("server.contextPath");
		return "http://localhost:" + port + "/" + path;
	}

}
