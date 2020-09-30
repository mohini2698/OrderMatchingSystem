package com.citi.controller;

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

import com.citi.dao.BidDAO;
import com.citi.dao.ExecutedDAO;
import com.citi.dao.OrderDAO;
import com.citi.dao.PendingDAO;
import com.citi.dao.RejectedDAO;
import com.citi.entity.BidTable;
import com.citi.entity.ExecutedTable;
import com.citi.entity.OfferTable;
import com.citi.entity.OrderGenerator;
import com.citi.entity.PendingTable;
import com.citi.entity.RejectedTable;
import com.citi.service.IBidService;
import com.citi.service.IOfferService;
import com.citi.service.OMS_Service;

@RestController
@CrossOrigin
public class OrderController {
	
	Logger logger = LoggerFactory.getLogger(OrderController.class);
	 
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
	

	
	//to get all orders from table
	@RequestMapping(value="/orders",method = RequestMethod.GET)
	public ResponseEntity<List<OrderGenerator>> findAllOrders(@RequestBody OrderGenerator order)
	{
		logger.debug("in get list of all orders");
		List<OrderGenerator> orders=dao.findAll();
		logger.debug(" all orders"+orders);
		ResponseEntity<List<OrderGenerator>> response=new ResponseEntity<List<OrderGenerator>>(orders,HttpStatus.FOUND);
		return response;
	}
	
	//to get all orders from executed table
	@RequestMapping(value="/executed",method = RequestMethod.GET)
	public ResponseEntity<List<ExecutedTable>> findAllOrders(@RequestBody ExecutedTable order)
	{
		logger.debug("in get list of allExecutedOrders");
		List<ExecutedTable> orders=exdao.findAll();
		logger.debug(" All Executed Orders "+orders);
		ResponseEntity<List<ExecutedTable>> response=new ResponseEntity<List<ExecutedTable>>(orders,HttpStatus.FOUND);
		return response;
	}
	
	//to get all orders from rejected table
	@RequestMapping(value="/rejected",method = RequestMethod.GET)
	public ResponseEntity<List<RejectedTable>> findAllOrders(@RequestBody RejectedTable order)
	{
		logger.debug("in get list of All Rejected orders");
		List<RejectedTable> orders=rdao.findAll();
		logger.debug(" All Rejected Orders "+orders);
		ResponseEntity<List<RejectedTable>> response=new ResponseEntity<List<RejectedTable>>(orders,HttpStatus.FOUND);
		return response;
	}
	
	
	//to add orders in pending table
	@RequestMapping(value="/orders",method = RequestMethod.POST)
	public ResponseEntity<OrderGenerator> saveOrder(@RequestBody OrderGenerator order)
	{
		logger.debug("in post list of orders");
		OrderGenerator added=dao.save(order);
		logger.debug(" Added order: "+added);
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
