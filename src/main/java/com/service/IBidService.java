package com.service;

import java.util.List;

import com.pojo.BidTable;
import com.pojo.OfferTable;

public interface IBidService {

	List<BidTable> findAllOrderByPriceDescDateAsc();	
	//List<OfferTable> findAllOrderByPriceDescDateAsc();	
	
}
