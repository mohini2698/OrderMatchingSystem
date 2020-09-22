package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pojo.OrderGenerator;
@Repository
public interface OrderDAO extends JpaRepository<OrderGenerator, Integer> {

}
