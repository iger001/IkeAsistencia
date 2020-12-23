package com.ike.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MsIkeOauthApplication implements CommandLineRunner {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(MsIkeOauthApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		for(int i=1; i<=5; i++) {
			String password = "Pa$$word0"+i;
			String bcr = passwordEncoder.encode(password);
			System.out.println("password : " + password + " hash : " + bcr);
		}
	}

}
