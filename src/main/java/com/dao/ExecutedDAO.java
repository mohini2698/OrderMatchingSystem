package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pojo.ExecutedTable;

@Repository
public interface ExecutedDAO extends JpaRepository<ExecutedTable, Integer> {

}
