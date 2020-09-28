package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pojo.BidTable;

@Repository
public interface BidDAO extends JpaRepository<BidTable, Integer>{
	
	
	@Query("FROM oms_bid ORDER BY price desc,time asc")
	List<BidTable> findByOrderByPriceDescDateAsc();

}
