package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pojo.RejectedTable;

public interface RejectedDAO extends JpaRepository<RejectedTable, Integer> {

}
