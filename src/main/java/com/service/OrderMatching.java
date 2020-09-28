package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderMatching {

	@Autowired
	BidService bidservice;
	
	public void Show()
	{
		bidservice.findByOrderByPriceDescDateAsc();
	}
}
