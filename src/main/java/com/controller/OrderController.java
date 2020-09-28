package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.BidDAO;
import com.dao.ExecutedDAO;
import com.dao.OrderDAO;
import com.dao.RejectedDAO;
import com.pojo.BidTable;
import com.pojo.ExecutedTable;
import com.pojo.OrderGenerator;
import com.pojo.RejectedTable;
import com.service.IBidService;

@RestController
public class OrderController {
	@Autowired
	OrderDAO dao;
	
	@Autowired
	ExecutedDAO exdao;
	
	@Autowired
	RejectedDAO rdao;
	
	
	@Autowired
	BidDAO bdao;
	
	
	@Autowired
	private IBidService bidservice;
	
	
	//to get all orders from pending table
	@GetMapping("/orders")
	public ResponseEntity<List<OrderGenerator>> findAllOrders(@RequestBody OrderGenerator order)
	{
		List<OrderGenerator> orders=dao.findAll();
		ResponseEntity<List<OrderGenerator>> response=new ResponseEntity<List<OrderGenerator>>(orders,HttpStatus.FOUND);
		return response;
	}
	
	//to get all orders from executed table
	@GetMapping("/executed")
	public ResponseEntity<List<ExecutedTable>> findAllOrders(@RequestBody ExecutedTable order)
	{
		List<ExecutedTable> orders=exdao.findAll();
		ResponseEntity<List<ExecutedTable>> response=new ResponseEntity<List<ExecutedTable>>(orders,HttpStatus.FOUND);
		return response;
	}
	
	//to get all orders from rejected table
	@GetMapping("/rejected")
	public ResponseEntity<List<RejectedTable>> findAllOrders(@RequestBody RejectedTable order)
	{
		List<RejectedTable> orders=rdao.findAll();
		ResponseEntity<List<RejectedTable>> response=new ResponseEntity<List<RejectedTable>>(orders,HttpStatus.FOUND);
		return response;
	}
	
	
	//to add orders in pending table
	@PostMapping("/orders")
	public ResponseEntity<OrderGenerator> saveOrder(@RequestBody OrderGenerator order)
	{
		OrderGenerator added=dao.save(order);
		
		ResponseEntity<OrderGenerator> response=new ResponseEntity<OrderGenerator>(added,HttpStatus.CREATED);
		return response;
	}
	
	
	//bid get price quantity
	
	
	
	//offer get price quantity
	
	
	
	//if both can be possible in get mapping    //nested query ig
	
	
	
	@GetMapping("/show")
	//findByOrderByPriceDescDateAsc
    public List<BidTable> findByOrderByPriceDescDateAsc() {

        return bidservice.findAllOrderByPriceDescDateAsc();
    }

	
}
