package com.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.backoff.Sleeper;
import org.springframework.stereotype.Service;

import com.dao.ExecutedDAO;
import com.dao.OrderDAO;
import com.dao.RejectedDAO;
import com.pojo.OrderGenerator;

@Service
public class OMS_Service {


	
	
	//to insert data in db
	
	@Autowired
	private OrderDAO orderdao;

	public OMS_Service(OrderDAO orderdao) {
		
		this.orderdao = orderdao;
	}
	

	@PostConstruct
	public void loaddata() {
		
		
		
		  long startTime = System.currentTimeMillis();
	      long endTime = startTime+(60 * 60*1000);
	               
	      while(System.currentTimeMillis()<endTime){
	    	  
	    	OrderGenerator order=new OrderGenerator();
	  		//get from generate order method
	  		RandomOrders ro=new RandomOrders();
	          
	    	order.setBid_offer(ro.getBid_offer());
	  		order.setOrderType(ro.getOrderType());
	  		
	  		if(order.getOrderType()=="limit")
	  		{
	  			order.setPrice(ro.getPrice());
	  		}
	  		else
	  		{
	  			order.setPrice(0);
	  		}
	  		
	  		order.setQuantity(ro.getQuantity());
	  		order.setDate(ro.getDate());
	  		
	  		
	  		orderdao.save(order);
	  		
	  		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	  
	      }  
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
