package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BoardCode;

@Repository
public interface BoardCodeDAO extends JpaRepository<BoardCode, Integer> {
	
	
	@Query(value="select b_code from boardcode where b_name=?", nativeQuery = true)
	public int findBCodeByBName(String b_name);
}
