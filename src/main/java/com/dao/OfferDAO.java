package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pojo.OfferTable;

@Repository
public interface OfferDAO extends JpaRepository<OfferTable, Integer> {

}
