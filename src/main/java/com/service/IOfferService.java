package com.service;

import java.util.List;


import com.pojo.OfferTable;

public interface IOfferService {

	List<OfferTable> findAllOrderByPriceAscDateAsc();	
}
