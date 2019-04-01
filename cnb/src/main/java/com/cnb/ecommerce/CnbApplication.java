package com.cnb.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.User;

@SpringBootApplication
public class CnbApplication {

	public static void main(String[] args) {
		SpringApplication.run(CnbApplication.class, args);
	}
	
//	@Override
//	public void run(String... args) throws Exception {
//		User user1 = new User();
//		user1.setFirstName("John");
//		user1.setLastName("Adams");
//		user1.setUsername("j");
//		user1.setPassword(SecurityUtility.passwordEncoder().encode("p"));
//		user1.setEmail("JAdams@gmail.com");
//	}
}
