package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UsersDAO;
import com.example.demo.entity.Users;

@Service
public class UsersService {

	@Autowired
	private UsersDAO dao;
	
	public Users findById() {
		return dao.findById(101).orElse(null); // .orElse(null)을 사용하는 이유 : finById(1)를 조회했을 때 값이 없으면 null로 처리하기 위함.
	}
	
	public void update(Users u) {
		dao.save(u);
	}
}
