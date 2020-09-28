package com.service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.retry.backoff.Sleeper;
import org.springframework.stereotype.Service;

import com.dao.BidDAO;
import com.dao.ExecutedDAO;
import com.dao.OfferDAO;
import com.dao.OrderDAO;
import com.dao.RejectedDAO;
import com.pojo.BidTable;
import com.pojo.OfferTable;
import com.pojo.OrderGenerator;

@Service
public class OMS_Service implements IBidService,IOfferService{


	
	
	//to insert data in db
	
	@Autowired
	private OrderDAO orderdao;
	
	
	@Autowired
	private BidDAO biddao;
	
	@Autowired
	private OfferDAO offerdao;
		
		
		

	public OMS_Service(OrderDAO orderdao, BidDAO biddao, OfferDAO offerdao) {
		
		this.orderdao = orderdao;
		this.biddao = biddao;
		this.offerdao = offerdao;
	}

	
	
	@PostConstruct
	public void checkdatabase() {
		
		int orderId = 1; 
		boolean exists = orderdao.existsById(orderId);
		
		if(exists==false)
		{
			loaddata();
		}
		
		
		int bidId = 1; 
		boolean exists1 = biddao.existsById(orderId);
		
		if(exists1==false)
		{
			separatedata();
		}

		
	}

	
	public void loaddata() {
		
		
		
		  long startTime = System.currentTimeMillis();
	      long endTime = startTime+(60 * 60*1000);
	               
	      while(System.currentTimeMillis()<endTime){
	    	  
	    	OrderGenerator order=new OrderGenerator();
	  		//get from generate order method
	  		RandomOrders ro=new RandomOrders();
	          
	    	order.setBid_offer(ro.getBid_offer());
	  		order.setOrderType(ro.getOrderType());
	  		order.setPrice(ro.getPrice());
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
	
	//put in bid / offer
	
	public void separatedata(){

		//orderdao.findAll();
		
		List<OrderGenerator> order = orderdao.findAll();
  		
		
		for(OrderGenerator i: order)
		{
			int id=i.getOrderId();
	  		String category=i.getBid_offer();
	  		String type=i.getOrderType();
	  		double price=i.getPrice();
	  		int q=i.getQuantity();
	  		Date d=i.getDate();
	  		
	  		
	  		
	  		
	  		if(i.getBid_offer().equals("bid"))
	  		{
	  			System.out.println(i.getBid_offer()+" if");
	  			BidTable bid=new BidTable();
	  			bid.setOrderType(type);
	  			bid.setPrice(price);
	  			bid.setQuantity(q);
	  			bid.setDate(d);
	  			bid.setOrderId(i.getOrderId());
	  			
	  			biddao.save(bid);
	  		}
	  		else
	  		{
	  			System.out.println(i.getBid_offer()+" else");
	  			OfferTable offer=new OfferTable();
	  			offer.setOrderType(type);
	  			offer.setPrice(price);
	  			offer.setQuantity(q);
	  			offer.setDate(d);
	  			offer.setOrderId(i.getOrderId());
	  			
	  			offerdao.save(offer);
	  		}
	  	
		}
    	
  		
  	
  		
  			
	}



	@Override
	public List<BidTable> findAllOrderByPriceDescDateAsc() {
		// TODO Auto-generated method stub
		
		return biddao.findByOrderByPriceDescDateAsc();
	}
	
	
	@Override
	public List<OfferTable> findAllOrderByPriceDescDateAsc() {
		// TODO Auto-generated method stub
		
		return offerdao.findByOrderByPriceDescDateAsc();
	}
	
	
	
	
	
	
	
	
	
}
