package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pojo.ExecutedTable;

public interface ExecutedDAO extends JpaRepository<ExecutedTable, Integer> {

}
