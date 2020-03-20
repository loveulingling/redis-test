package com.acorus.redis.test;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class Demo {
	
	@Test
	public void test() {
		Jedis jedis = new Jedis("192.168.91.101", 6379);
		String pong = jedis.ping();
		System.out.println(pong);
		jedis.set("str1", "小楼一夜听春雨");
		Set<String> keys = jedis.keys("*");
		for (String key : keys) {
			System.out.println(key);
		}
		List<String> list = jedis.lrange("list1", 0, -1);
		for (String li : list) {
			System.out.println(li);
		}
		String str1 = jedis.get("str1");
		System.out.println(str1);
		jedis.close();
	}
}
