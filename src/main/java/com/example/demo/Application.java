package com.example.demo;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.responsive.RoleRepository;
import com.example.demo.responsive.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;

//import com.example.demo.entity.Inter;
//import com.example.demo.entity.Menter;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	
//	@Bean
//	CommandLineRunner run(RoleRepository roleRepo) {
//		return args -> {
//
//			roleRepo.save(new Role("ADMIN"));
//			roleRepo.save(new Role("INTER"));
//			roleRepo.save(new Role("MENTOR"));
//
//		};
//	}
	
	

}
