package com.pojo;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name="oms_rejected")
public class RejectedTable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int rejected_id;
	
	public String orderType;
	public String bid_offer;
	public double price;
	public int quantity;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	Date date=new Date();
	
	@OneToOne
	private OrderGenerator ordergenerator;

	
	
	public RejectedTable() {
		//super();
	}



	public RejectedTable(int rejected_id, String orderType, String bid_offer, double price, int quantity, Date date,
			OrderGenerator ordergenerator) {
		super();
		this.rejected_id = rejected_id;
		this.orderType = orderType;
		this.bid_offer = bid_offer;
		this.price = price;
		this.quantity = quantity;
		this.date = date;
		this.ordergenerator = ordergenerator;
	}



		@Override
		public String toString() {
			return "RejectedTable [rejected_id=" + rejected_id + ", orderType=" + orderType + ", bid_offer=" + bid_offer
					+ ", price=" + price + ", quantity=" + quantity + ", date=" + date + ", ordergenerator="
					+ ordergenerator + "]";
		}



	public int getRejected_id() {
		return rejected_id;
	}



	public void setRejected_id(int rejected_id) {
		this.rejected_id = rejected_id;
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



	public OrderGenerator getOrdergenerator() {
		return ordergenerator;
	}



	public void setOrdergenerator(OrderGenerator ordergenerator) {
		this.ordergenerator = ordergenerator;
	}

	

	


		
}
