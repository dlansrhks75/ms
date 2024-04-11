package com.example.demo.dao;

import com.example.demo.entity.RegionCode;
import com.example.demo.entity.Users;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDAO extends JpaRepository<Users, Integer> {


	@Modifying
	@Query(value = "update users set u_name=?, u_email=?, u_phone=?, u_nickname=?, u_fname=?, rno=? where uno=?", nativeQuery = true)
	@Transactional
	public int updateInfo(String u_name, String u_email, String u_phone, String u_nickname, String u_fname, String rno, int uno);

	@Modifying
	@Query(value = "update users set u_pwd=? where p.uno=?", nativeQuery = true)
	@Transactional
	public void updatePwd(String newPwd, int uno);


}
