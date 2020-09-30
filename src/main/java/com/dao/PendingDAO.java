package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pojo.OrderGenerator;
import com.pojo.PendingTable;

@Repository
public interface PendingDAO extends JpaRepository<PendingTable, Integer>{

}
