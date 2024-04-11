package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PuppyDAO;
import com.example.demo.entity.Puppy;

@Service
public class PuppyService {
	
	@Autowired
	private PuppyDAO dao;
	
	
	public List<Puppy> findByUno(int uno) {
		return findByUno(uno);
	}

}
