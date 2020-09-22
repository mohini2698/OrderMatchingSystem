package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.OrderDAO;
import com.pojo.OrderGenerator;

@RestController
public class OrderController {
	@Autowired
	OrderDAO dao;
	@GetMapping("/orders")
	public ResponseEntity<List<OrderGenerator>> findAllOrders(@RequestBody OrderGenerator order)
	{
		List<OrderGenerator> orders=dao.findAll();
		ResponseEntity<List<OrderGenerator>> response=new ResponseEntity<List<OrderGenerator>>(orders,HttpStatus.FOUND);
		return response;
	}
	
	
	
	

}
