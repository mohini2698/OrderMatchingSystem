package com.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name="oms_rejected")
public class RejectedTable implements Serializable{

	public String orderType;
	public String bid_offer;
	public double price;
	public int quantity;
	Date date=new Date();
	
	
	@Id
	public int rejected_id;
	
	@OneToOne
	private OrderGenerator ordergenerator;

	
	
	public RejectedTable() {
		//super();
	}



	


	public RejectedTable(String orderType, String bid_offer, double price, int quantity, Date date, int rejected_id,
			OrderGenerator ordergenerator) {
		super();
		this.orderType = orderType;
		this.bid_offer = bid_offer;
		this.price = price;
		this.quantity = quantity;
		this.date = date;
		this.rejected_id = rejected_id;
		this.ordergenerator = ordergenerator;
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



	
	
	
	
	
}
