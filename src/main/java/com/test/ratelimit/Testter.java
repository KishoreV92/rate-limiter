package com.test.ratelimit;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.test.ratelimit.impl.SlidingWindowRateLimiter;

public class Testter {

	public static void main(String[] args) throws InterruptedException {

		// Define a formatter (optional)
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

		RateLimiter limiter = new SlidingWindowRateLimiter(2, 5);
		for (int i = 0; i < 20; i++) {
			boolean result = limiter.tryConsume("user1");
			System.out.println("User1" + result + ":" + LocalTime.now().format(formatter));
			boolean result2 = limiter.tryConsume("user2");
			System.out.println("User2" + result2 + ":" + LocalTime.now().format(formatter));
		}
	}
}
