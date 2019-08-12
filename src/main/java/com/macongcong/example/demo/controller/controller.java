package com.macongcong.example.demo.controller;

import java.awt.List;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.macongcong.example.demo.bean.User;
import com.macongcong.example.demo.utils.SqlMain;
import com.macongcong.example.demo.utils.TestStringUtils;

@Controller
public class controller {
	@Resource
	private RedisTemplate<String , Object> redisTemplate = new RedisTemplate<String , Object>() ;
	
	@RequestMapping("jdk")
	public void test1() throws UnsupportedEncodingException{
		//创建对象22分
		int o = (int)Math.random()*100;
		int p = (int)Math.random()*520;
		String birth =(p+18)+"";
		int i;
		User user ;
		String sex;
		if(o>50) {
			sex = "男";
		}else {
			sex = "女";
		}
		//jdk序列化
		long jdk_start = System.currentTimeMillis();
		for (i = 0; i < 100000; i++) {
			user = new User(i,SqlMain.getChinaName(),sex,"13"+TestStringUtils.getRandomString(10),"@qq.com",birth);
			StringRedisSerializer s = new StringRedisSerializer();
			JdkSerializationRedisSerializer jdk = new JdkSerializationRedisSerializer();
			ListOperations<String, Object> opsForList = redisTemplate.opsForList();
			opsForList.leftPush(""+s.serialize(((i+1)+"")),jdk.serialize(user));
		}
		long jdk_end = System.currentTimeMillis();
		System.out.println("jdk序列化"+i+"个,所需时间:"+(jdk_end-jdk_start));
	} 
	@RequestMapping("json")
	public void test2() throws UnsupportedEncodingException{
		//创建对象22分
		int o = (int)Math.random()*100;
		int p = (int)Math.random()*520;
		String birth =(p+18)+"";
		int i;
		User user ;
		String sex;
		if(o>50) {
			sex = "男";
		}else {
			sex = "女";
		}
		//jdk序列化
		long json_start = System.currentTimeMillis();
		for (i = 0; i < 100000; i++) {
			user = new User(i,SqlMain.getChinaName(),sex,"13"+TestStringUtils.getRandomString(10),"@qq.com",birth);
			StringRedisSerializer s = new StringRedisSerializer();
			Jackson2JsonRedisSerializer json = new Jackson2JsonRedisSerializer(List.class);
			ListOperations<String, Object> opsForList = redisTemplate.opsForList();
			opsForList.leftPush(""+s.serialize(((i+1)+"")),json.serialize(user));
		}
		long json_end = System.currentTimeMillis();
		System.out.println("json序列化"+i+"个,所需时间:"+(json_end-json_start));
	} 
	@RequestMapping("hash")
	public void test3() throws UnsupportedEncodingException{
		//创建对象22分
		int o = (int)Math.random()*100;
		int p = (int)Math.random()*520;
		String birth =(p+18)+"";
		int i;
		User user ;
		String sex;
		if(o>50) {
			sex = "男";
		}else {
			sex = "女";
		}
		//jdk序列化
		long hash_start = System.currentTimeMillis();
		for (i = 0; i < 100000; i++) {
			user = new User(i,SqlMain.getChinaName(),sex,"13"+TestStringUtils.getRandomString(10),"@qq.com",birth);
			StringRedisSerializer s = new StringRedisSerializer();
			HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
			opsForHash.put(""+s.serialize("user"), ""+s.serialize((i+1)+""),s.serialize(user.toString()));
		}
		long hash_end = System.currentTimeMillis();
		System.out.println("hash序列化"+i+"个,所需时间:"+(hash_end-hash_start));
	} 
}
