package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UsersDAO;
import com.example.demo.entity.RegionCode;
import com.example.demo.entity.Users;

@Service
public class UsersService {

	@Autowired
	private UsersDAO dao;
	
	public Users findById(int uno) {
		return dao.findById(uno).orElse(null); // .orElse(null)을 사용하는 이유 : finById(1)를 조회했을 때 값이 없으면 null로 처리하기 위함.
	}
	
	public int updateInfo(String u_name, String u_email, String u_phone, String u_nickname, String u_fname, String rno, int uno) {
		return dao.updateInfo(u_name, u_email, u_phone, u_nickname, u_fname, rno, uno);
	}
	
	public void updatePwd(String newPwd, int uno) {
		dao.updatePwd(newPwd, uno);
	}
	
}
