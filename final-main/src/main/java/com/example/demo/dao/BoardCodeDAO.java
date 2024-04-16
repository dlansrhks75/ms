package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BoardCode;

@Repository
public interface BoardCodeDAO extends JpaRepository<BoardCode, Integer> {

	@Query(value = "select b_name from boardcode where b_code=?", nativeQuery = true)
	public String findById(int boardCode);
}
