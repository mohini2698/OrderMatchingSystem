package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ExecutedDAO;
import com.dao.OrderDAO;
import com.dao.RejectedDAO;

@Service
public class OMS_Service {

	
	@Autowired
	OrderDAO dao;
	
	@Autowired
	ExecutedDAO exdao;
	
	@Autowired
	RejectedDAO rdao;
	
}
