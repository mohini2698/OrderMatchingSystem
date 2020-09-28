package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.dao.BidDAO;
import com.dao.OfferDAO;
import com.dao.OrderDAO;

@SpringBootApplication(scanBasePackages = "com")
@EntityScan(basePackages = "com")
@EnableJpaRepositories(basePackages = "com")
public class OrderMatchingSystemApplication {

	
	@Autowired
	OrderDAO orderDAO;
	
	@Autowired
	BidDAO biddao;
	
	@Autowired
	OfferDAO offerdao;
	
	public static void main(String[] args) {
		SpringApplication.run(OrderMatchingSystemApplication.class, args);
	
	}

}
