package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.OrderDAO;

@Service
public class OMS_Service {

	
	@Autowired
	OrderDAO dao;
	
}
