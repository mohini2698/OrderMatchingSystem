package com.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.pojo.ExecutedTable;
import com.pojo.OfferTable;
//import com.pojo.Order;
import com.pojo.OrderGenerator;
import com.pojo.RejectedTable;

@Service
public class OMS_Service implements IBidService, IOfferService 
{

	//Logger logger = LoggerFactory.getLogger(OMS_Service.class);

	// to insert data in db

	@Autowired
	private OrderDAO orderdao;

	@Autowired
	private BidDAO biddao;

	@Autowired
	private OfferDAO offerdao;

	@Autowired
	private ExecutedDAO executeddao;
	
	@Autowired
	private RejectedDAO rejecteddao;
	
	
	static int bidcount=0;
	static int offercount=0;

	

	public OMS_Service(OrderDAO orderdao, BidDAO biddao, OfferDAO offerdao, ExecutedDAO executeddao,
			RejectedDAO rejecteddao) {
		
		//this.logger = logger;
		this.orderdao = orderdao;
		this.biddao = biddao;
		this.offerdao = offerdao;
		this.executeddao = executeddao;
		this.rejecteddao = rejecteddao;
	}

	@PostConstruct
	public void checkdatabase() {

//		int orderId = 1;
//		boolean exists = orderdao.existsById(orderId);
//
//		if (exists == false) {
//			loaddata();
//	 	}
		
		
		//separatedata();

//		int bidId = 1;
//		boolean exists1 = biddao.existsById(orderId);
//
//		if (exists1 == false) {
//			separatedata();
//		}
		System.out.println("***********************************************************************************************8in post construct");
		OrderBook();

		// ************

	}

	public void loaddata() {        

		long startTime = System.currentTimeMillis();
		long endTime = startTime + (10000);

		while (System.currentTimeMillis() < endTime) {

			OrderGenerator order = new OrderGenerator();
			// get from generate order method
			RandomOrders ro = new RandomOrders();

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

	// put in bid / offer

	public void separatedata() {

		// orderdao.findAll();

		List<OrderGenerator> order = orderdao.findAll();

		for (OrderGenerator i : order) {
			int id = i.getOrderId();
			String category = i.getBid_offer();
			String type = i.getOrderType();
			double price = i.getPrice();
			int q = i.getQuantity();
			Date d = i.getDate();

			if (i.getBid_offer().equals("bid")) {
				System.out.println(i.getBid_offer() + " if");
				BidTable bid = new BidTable();
				bid.setOrderType(type);
				bid.setPrice(price);
				bid.setQuantity(q);
				bid.setDate(d);
				bid.setOrderId(i.getOrderId());

				biddao.save(bid);
			} else {
				System.out.println(i.getBid_offer() + " else");
				OfferTable offer = new OfferTable();
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
		//logger.debug("in list of bid table method");
		return biddao.findByOrderByPriceDescDateAsc();
	}

	@Override
	public List<OfferTable> findAllOrderByPriceAscDateAsc() {
		// TODO Auto-generated method stub
		return offerdao.findAllOrderByPriceAscDateAsc();
	}

	public void saveExecuted() {

	}
	
	
	public void OrderBook()
	{
		List<OrderGenerator> order = orderdao.findAll();
		List<BidTable> bid=new ArrayList<>();
		List<OfferTable> offer=new ArrayList<>();
		System.out.println("\n Importing Orders: ");
		
		int id=0;
		String category=null;
		String type=null;
		double price=0;
		int q=0;
		Date d=null;
		int offerid = 1;
		int bidid = 1;

		for(OrderGenerator o: order)
		{
			System.out.println("\n*********************************************************** order o");
			id=o.getOrderId();
			category=o.getBid_offer();
			type=o.getOrderType();
			price=o.getPrice();
			q=o.getQuantity();
			d=o.getDate();
		
			
			
			// if bid
			if (category.equals("bid")) 
			{
				System.out.println("\n***********************inside bid");
				if(offercount==0)
				{
					if(type.equals("limit"))
					{
						System.out.println("\n***********************inside limit");
					BidTable temp = new BidTable(bidid,type, price, q,d,id);
					bid.add(temp);
					bidid++;
					bidcount++;
					
					}
					else
					{
						//put in rejected
						RejectedTable rejected=new RejectedTable();
						
						rejected.setBid_offer("bid");
						rejected.setDate(new Date());
						rejected.setOrderType(o.orderType);
						rejected.setPrice(0);
						rejected.setQuantity(o.quantity);
						
						
						
						
						System.out.println("***********************************No Offer - order is rejected");
					}
				}
				else
				{
					BidTable temp = new BidTable(bidid,type, price, q,d,id);
					bid.add(temp);
					bidid++;
					bidcount++;
					OrderMatching(bid, offer,"bid");
				}
				
			}

			else // if offer
			{
				System.out.println("\n***********************inside offer");
				if(bidcount==0)
				{
					if(type.equals("limit"))
					{
						System.out.println("\n***********************inside offer limit");
					OfferTable temp = new OfferTable(offerid,type, price, q,d,id);
					offer.add(temp);
					offercount++;
					offerid++;
					}
					else
					{
						
						//put in rejected
						RejectedTable rejected=new RejectedTable();
						
						rejected.setBid_offer("offer");
						rejected.setDate(new Date());
						rejected.setOrderType(o.orderType);
						rejected.setPrice(0);
						rejected.setQuantity(o.quantity);
						
						System.out.println("*************************No Bid - order is rejected");

					}
				}
				else
				{
					OfferTable temp = new OfferTable(offerid,type, price, q,d,id);
					offer.add(temp);
					offerid++;
					offercount++;
					
					OrderMatching(bid, offer, "offer");
				}
				
			}

			System.out.println("**********"+bid);
			System.out.println("**********"+offer);
		
		}
		
		
	}
	
	
	public void OrderMatching(List<BidTable> bid,List<OfferTable> offer,String str) 
	{
		System.out.println("\n***********************inside order match fun");		
			
			//bid
			if(str.equals("bid"))
			{
				
				int flag = 0;
					
					//limit bid
				if (bid.get(bid.size() - 1).orderType.equals("limit"))
					{
						Iterator<OfferTable> i = offer.iterator();
						
							while (i.hasNext()) 
							{
								OfferTable of = i.next();
								if (of.price <= bid.get(bid.size() - 1).price) 
								{
									System.out.println("Bid id" + bid.get(bid.size() - 1).bidId + "matched w offer id: " + of.offerId);
									if (bid.get(bid.size() - 1).quantity == of.quantity)
									{
										//put in executed
										ExecutedTable executed=new ExecutedTable();
										executed.setBidId(bid.get(bid.size() - 1).bidId);
										executed.setOfferId(of.offerId);
										executed.setPrice(of.price);
										executed.setQuantity(of.quantity);
										executed.setDate(new Date());
										
										executeddao.save(executed);
										
										
										i.remove();
										bid.remove(bid.size() - 1);
										bidcount--;
										offercount--;
										flag = 1;
										
										
										
										
										
										break;
										

									} else if (bid.get(bid.size() - 1).quantity > of.quantity) 
									{

										//System.out.println("Partial execution of bid w offer id: " + of.offerId);
										int qty_rem = bid.get(bid.size() - 1).quantity - of.quantity;
										bid.get(bid.size() - 1).quantity = qty_rem;
										//put in executed
										ExecutedTable executed=new ExecutedTable();
										executed.setBidId(bid.get(bid.size() - 1).bidId);
										executed.setOfferId(of.offerId);
										executed.setPrice(of.price);
										executed.setQuantity(of.quantity);
										executed.setDate(new Date());
										executeddao.save(executed);
										
										
										i.remove();
										offercount--;
										
									} else if (bid.get(bid.size() - 1).quantity < of.quantity) 
									{
										System.out.println("Partial execution");
										int qty_rem = of.quantity - bid.get(bid.size() - 1).quantity;
										of.quantity = qty_rem;
										
										//put in executed
										ExecutedTable executed=new ExecutedTable();
										executed.setBidId(bid.get(bid.size() - 1).bidId);
										executed.setOfferId(of.offerId);
										executed.setPrice(of.price);
										executed.setQuantity(bid.get(bid.size() - 1).quantity);
										executed.setDate(new Date());
										
										
										
										executeddao.save(executed);
										bid.remove(bid.size() - 1);
										bidcount--;
										flag = 1;
										break;
									}

									
								}
								
								
							

						}
						if (flag == 0) {
							System.out.println("No match found for bid id: " + bid.get(bid.size() - 1).bidId
									+ " Entering into Bid pending list");

						}

						

					} 
					
					// Market bid
					else if (bid.get(bid.size() - 1).orderType.equals("market")) 
					{
									Iterator<OfferTable> i = offer.iterator();
									
									while (i.hasNext()) 
									{
										OfferTable of_min = Collections.min(offer, Comparator.comparing(s -> s.getPrice()));

										if (bid.get(bid.size() - 1).quantity == of_min.quantity) {

											//System.out.println("Bid w bid id: " + bid.get(bid.size() - 1).bidId
											//		+ "has been matched w offerId: " + of_min.getOfferId());
											//put in executed
											
											ExecutedTable executed=new ExecutedTable();
											executed.setBidId(bid.get(bid.size() - 1).bidId);
											executed.setOfferId(of_min.offerId);
											executed.setPrice(of_min.price);
											executed.setQuantity(bid.get(bid.size() - 1).quantity);
											executed.setDate(new Date());
											
											
											
											executeddao.save(executed);
											
											
											
											
											bid.remove(bid.size() - 1);
											offer.removeIf(obj -> obj.offerId == of_min.getOfferId());
											bidcount--;
											offercount--;
											flag=1;
											break;

										} else if (bid.get(bid.size() - 1).quantity > of_min.getQuantity()) {
											int qty_offers=0;
											for(OfferTable of:offer){
												qty_offers+=of.quantity;
											}
											if(qty_offers<bid.get(bid.size() - 1).quantity )
											{
												System.out.println("Bid id: "+bid.get(bid.size() - 1).bidId+"rejected since the quantity can't be satisfied");
												
												//put in rejected
												RejectedTable rejected=new RejectedTable();
												
												rejected.setBid_offer("bid");
												rejected.setDate(new Date());
												rejected.setOrderType(bid.get(bid.size() - 1).orderType);
												rejected.setPrice(bid.get(bid.size() - 1).price);
												rejected.setQuantity(bid.get(bid.size() - 1).quantity);
												
												
												rejecteddao.save(rejected);
												
												break;
												
												
												
												
											}
											else// if((qty_offers>=bid.get(bid.size() - 1).quantity))
											{
												System.out.println("Partial execution of Bid w bid id: " + bid.get(bid.size() - 1).bidId
													+ "has been matched w offerId: " + of_min.getOfferId());
												int qty_rem = bid.get(bid.size() - 1).quantity - of_min.getQuantity();
												//bid.get(bid.size() - 1).quantity=qty_rem;
												//of_min.setQuantity(qty_rem);
												
									
												
												
												
												
												
												bid.get(bid.size() - 1).quantity = qty_rem;
												
												//put in executed
												ExecutedTable executed=new ExecutedTable();
												executed.setBidId(bid.get(bid.size() - 1).bidId);
												executed.setOfferId(of_min.offerId);
												executed.setPrice(of_min.price);
												executed.setQuantity(of_min.quantity);
												executed.setDate(new Date());
												
												
												executeddao.save(executed);
												
												offer.removeIf(obj -> obj.offerId == of_min.getOfferId());
												offercount--;
											}
											
											
											
										} else if (bid.get(bid.size() - 1).quantity < of_min.getQuantity()) {
											System.out.println("Bid w bid id: " + bid.get(bid.size() - 1).bidId
													+ "has been matched w offerId: " + of_min.getOfferId());
											int qty_rem = of_min.quantity - bid.get(bid.size() - 1).quantity;
											of_min.quantity=qty_rem;
											
											//put in executed
											ExecutedTable executed=new ExecutedTable();
											executed.setBidId(bid.get(bid.size() - 1).bidId);
											executed.setOfferId(of_min.offerId);
											executed.setPrice(of_min.price);
											executed.setQuantity(bid.get(bid.size() - 1).quantity);
											executed.setDate(new Date());
											
											
											executeddao.save(executed);
											
											
											
											bid.remove(bid.size() - 1);
											bidcount--;
											flag=1;
											break;
										}
									
										
									
									}
						
								
					}
				
			}
			
			
			
			//Offers
			else if(str.equals("offer"))// offer
			{
				System.out.println("***********************inside offer if else");
					int flag1 = 0;

					if (offer.get(offer.size() - 1).orderType.equals("limit")) 
					{

						Iterator<BidTable> i = bid.iterator();
						
							while (i.hasNext()) 
							{
								BidTable bi = i.next();
								if (offer.get(offer.size() - 1).price <= bi.price)
								{
									
									
									if (offer.get(offer.size() - 1).quantity == bi.quantity) {
										//put in executed
										ExecutedTable executed=new ExecutedTable();
										executed.setBidId(bi.bidId);
										executed.setOfferId(offer.get(offer.size() - 1).offerId);
										executed.setPrice(bi.price);
										executed.setQuantity(offer.get(offer.size() - 1).quantity);
										executed.setDate(new Date());
										
										
										executeddao.save(executed);
										
										
										
										i.remove();       
										offer.remove(offer.size() - 1);   
										bidcount--;
										offercount--;
										System.out.println("Offer id" + offer.get(offer.size() - 1).offerId + "matched w bid id: " + bi.bidId);
										flag1=1;
										break;

									} else if (offer.get(offer.size() - 1).quantity > bi.quantity) 
									{	
										
											System.out.println("Partial execution of offer w bid id: " + bi.bidId);
											int qty_rem = offer.get(offer.size() - 1).quantity - bi.quantity;
											offer.get(offer.size() - 1).quantity = qty_rem;
											
											
											
											//put in executed
											ExecutedTable executed=new ExecutedTable();
											executed.setBidId(bi.bidId);
											executed.setOfferId(offer.get(offer.size() - 1).offerId);
											executed.setPrice(bi.price);
											executed.setQuantity(bi.quantity);
											executed.setDate(new Date());
											
											
											executeddao.save(executed);
											
											
											
											
											
											i.remove();
											bidcount--;
										
										
									} else if (offer.get(offer.size() - 1).quantity < bi.quantity) {
										System.out.println();
										int qty_rem = bi.quantity - offer.get(bid.size() - 1).quantity;
										bi.quantity = qty_rem;
										
										
										//put in executed
										ExecutedTable executed=new ExecutedTable();
										executed.setBidId(bi.bidId);
										executed.setOfferId(offer.get(offer.size() - 1).offerId);
										executed.setPrice(bi.price);
										executed.setQuantity(offer.get(offer.size() - 1).quantity);
										executed.setDate(new Date());
										
										
										executeddao.save(executed);
										
										
										
										
										offer.remove(offer.size() - 1);
										offercount--;
										flag1=1;
										break;
										
									}

									
								}
							}
							
						
											
						if (flag1 == 0) {
							System.out.println("No match found for offer id: " + offer.get(offer.size() - 1).offerId
									+ " Entering into offer pending list");

						}

						

					}
					
					//market
					else if (offer.get(offer.size() - 1).orderType.equals("market")) 
					{
						
						System.out.println("******************inside market offer");
						Iterator<BidTable> i = bid.iterator();
						while (i.hasNext()) 
						{
							BidTable bid_max = Collections.max(bid, Comparator.comparing(s -> s.getPrice()));

							if (offer.get(offer.size() - 1).quantity == bid_max.getQuantity()) {
								System.out.println("Bid w bid id: " + bid.get(bid.size() - 1).bidId
										+ "has been matched w offerId: " + bid_max.getBidId());
								
								//put in executed
								ExecutedTable executed=new ExecutedTable();
								executed.setBidId(bid_max.bidId);
								executed.setOfferId(offer.get(offer.size() - 1).offerId);
								executed.setPrice(bid_max.price);
								executed.setQuantity(offer.get(offer.size() - 1).quantity);
								executed.setDate(new Date());
								
								
								executeddao.save(executed);
								
								
								
								
								
								offer.remove(offer.size() - 1);
								bid.removeIf(obj -> obj.bidId == bid_max.getBidId());
								bidcount--;
								offercount--;
								flag1=1;
								break;

							} else if (offer.get(offer.size() - 1).quantity > bid_max.getQuantity()) {
								int qty_bid=0;
								for(BidTable b:bid){
									qty_bid+=b.quantity;
								}
								if(qty_bid<offer.get(offer.size() - 1).quantity )
								{
									
									
									//put in rejected
									RejectedTable rejected=new RejectedTable();
									
									rejected.setBid_offer("offer");
									rejected.setDate(new Date());
									rejected.setOrderType(offer.get(offer.size() - 1).orderType);
									rejected.setPrice(offer.get(offer.size() - 1).price);
									rejected.setQuantity(offer.get(offer.size() - 1).quantity);
									
									
									rejecteddao.save(rejected);
									System.out.println("Bid id: "+bid.get(bid.size() - 1).bidId+"rejected since the quantity can't be satisfied");
								}
								else{
								System.out.println("Partial execution Offer w bid id: " + bid_max.getBidId()
										+ "has been matched w offerId: " + bid.get(bid.size() - 1).bidId);
								int qty_rem = offer.get(offer.size() - 1).quantity - bid_max.getQuantity();
								offer.get(offer.size() - 1).quantity = qty_rem;
								
								//put in executed
								ExecutedTable executed=new ExecutedTable();
								executed.setBidId(bid_max.bidId);
								executed.setOfferId(offer.get(offer.size() - 1).offerId);
								executed.setPrice(bid_max.price);
								executed.setQuantity(offer.get(offer.size() - 1).quantity);
								executed.setDate(new Date());
								
								
								executeddao.save(executed);
								
								
								
								
								bid.removeIf(obj -> obj.bidId == bid_max.getBidId());
								bidcount--;
								}
							} else if (offer.get(offer.size() - 1).quantity < bid_max.getQuantity()) {
								//System.out.println("loop :");
								int qty_rem = bid_max.getQuantity() - offer.get(offer.size() - 1).quantity;
								bid_max.setQuantity(qty_rem);
								
								//put in executed
								ExecutedTable executed=new ExecutedTable();
								executed.setBidId(bid_max.bidId);
								executed.setOfferId(offer.get(offer.size() - 1).offerId);
								executed.setPrice(bid_max.price);
								executed.setQuantity(offer.get(offer.size() - 1).quantity);
								executed.setDate(new Date());
								
								
								executeddao.save(executed);
								
								
								

								offer.remove(offer.size() - 1);
								offercount--;
								
								

								flag1=1;
								break;
							}
						}
						
//						if(flag1==0)
//						{
//							System.out.println("No match found for offer id: " + offer.get(offer.size() - 1).offerId
//									+ " Entering into offer pending list");
//						}
					}
					
					
					
					
					
					
				}
			}
	}

