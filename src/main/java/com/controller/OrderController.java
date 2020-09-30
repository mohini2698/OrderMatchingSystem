package com.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dao.BidDAO;
import com.dao.ExecutedDAO;
import com.dao.OrderDAO;
import com.dao.PendingDAO;
import com.dao.RejectedDAO;
import com.pojo.BidTable;
import com.pojo.ExecutedTable;
import com.pojo.OfferTable;
import com.pojo.OrderGenerator;
import com.pojo.PendingTable;
import com.pojo.RejectedTable;
import com.service.IBidService;
import com.service.IOfferService;
import com.service.OMS_Service;

@RestController
@CrossOrigin
public class OrderController {
	
	 //Logger logger = LoggerFactory.getLogger(OrderController.class);
	 
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
	
	
	@Autowired
	private IOfferService offerservice;
	
	
	@Autowired
	private PendingDAO pendingdao;
	
	//1.logger
	//2.ui logging
	//3.request mapping
	//4.packet refactor com.citi.dao com.citi.entity
	//5.Rest .. crossorigin
	
	//to get all orders from pending table
	//@GetMapping("/orders")
	@RequestMapping(value="/orders",method = RequestMethod.GET)
	public ResponseEntity<List<OrderGenerator>> findAllOrders(@RequestBody OrderGenerator order)
	{
	//	logger.debug("in get list of all orders");
		List<OrderGenerator> orders=dao.findAll();
		//logger.debug(" all orders"+orders);
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
	
	
	@RequestMapping(value="/pending",method = RequestMethod.GET)
	public ResponseEntity<List<PendingTable>> findAllOrders(@RequestBody PendingTable order)
	{
	//	logger.debug("in get list of all orders");
		List<PendingTable> orders=pendingdao.findAll();
		//logger.debug(" all orders"+orders);
		ResponseEntity<List<PendingTable>> response=new ResponseEntity<List<PendingTable>>(orders,HttpStatus.FOUND);
		return response;
	}
	
	
	
	@GetMapping("/show")
	//findByOrderByPriceDescDateAsc
    public List<BidTable> findByOrderByPriceDescDateAsc() {

        return bidservice.findAllOrderByPriceDescDateAsc();
    }

	
	
	@GetMapping("/show1")
	//findByOrderByPriceDescDateAsc
    public List<OfferTable> findByOrderByPriceAscDateAsc() {

        return offerservice.findAllOrderByPriceAscDateAsc();
    }

	
}
