package com.test.ratelimit.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.test.ratelimit.RateLimiter;

public class SlidingWindowRateLimiter implements RateLimiter {

	private int windowSize;
	private int maxCount;
	private Map<String, Queue<Long>> userWindowMap;

	public SlidingWindowRateLimiter(int windowSize, int maxCount) {
		this.windowSize = windowSize;
		this.maxCount = maxCount;
		userWindowMap = new HashMap<>();
	}

	@Override
	public synchronized boolean tryConsume(String key) {

		long currentTimeStamp = System.currentTimeMillis();
		long expirationTime = currentTimeStamp - windowSize;
		userWindowMap.putIfAbsent(key, new LinkedList<>());
		Queue<Long> timeStampQueue = userWindowMap.get(key);
		while (!timeStampQueue.isEmpty() && timeStampQueue.peek() <= expirationTime) {
			timeStampQueue.poll();
		}
		if (timeStampQueue.size() >= maxCount)
			return false;
		else {
			timeStampQueue.offer(currentTimeStamp);
			return true;
		}
	}

}
