package com.test.ratelimit;

public interface RateLimiter {

	boolean tryConsume(String key);
}
