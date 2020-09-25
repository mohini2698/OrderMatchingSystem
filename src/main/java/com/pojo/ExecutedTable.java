package com.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name="oms_executed")
public class ExecutedTable {
	
	@Id
	public int tradeId;
	
	
	
	public String orderType;
	public String bid_offer;
	public double price;
	public int quantity;
	Date date=new Date();
	
	
	@OneToOne
	private OrderGenerator ordergenerator;


	public ExecutedTable() {
		//super();
	}


	public ExecutedTable(int tradeId, String orderType, String bid_offer, double price, int quantity, Date date,
			OrderGenerator order_id) {
		super();
		this.tradeId = tradeId;
		this.orderType = orderType;
		this.bid_offer = bid_offer;
		this.price = price;
		this.quantity = quantity;
		this.date = date;
		this.ordergenerator = order_id;
	}


	public int getTradeId() {
		return tradeId;
	}


	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}


	public String getOrderType() {
		return orderType;
	}


	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}


	public String getBid_offer() {
		return bid_offer;
	}


	public void setBid_offer(String bid_offer) {
		this.bid_offer = bid_offer;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public OrderGenerator getOrder_id() {
		return ordergenerator;
	}


	public void setOrder_id(OrderGenerator order_id) {
		this.ordergenerator = order_id;
	}
	
	
	
	

}
