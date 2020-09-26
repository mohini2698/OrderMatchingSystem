package com.pojo;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name="oms_order")
public class OrderGenerator {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int orderId;
	
	public String orderType;
	public String bid_offer;
	public double price;
	public int quantity;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	Date date=new Date();
	
	
	public OrderGenerator() {
		
	}
	

	public OrderGenerator(int orderId, String orderType, String bid_offer, double price, int quantity, Date date) {
		super();
		this.orderId = orderId;
		this.orderType = orderType;
		this.bid_offer = bid_offer;
		this.price = price;
		this.quantity = quantity;
		this.date = date;
	}





	@Override
	public String toString() {
		return "OrderGenerator [orderId=" + orderId + ", orderType=" + orderType + ", bid_offer=" + bid_offer
				+ ", price=" + price + ", quantity=" + quantity + ", date=" + date + "]";
	}

	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
	
	
	
	
	
}
