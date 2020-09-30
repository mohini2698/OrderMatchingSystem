package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pojo.RejectedTable;

@Repository
public interface RejectedDAO extends JpaRepository<RejectedTable, Integer> {

}
