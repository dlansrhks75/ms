package com.example.demo.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Puppy;
import com.example.demo.entity.Schedule;

@Repository
public interface ScheduleDAO extends JpaRepository<Schedule, Integer> {
	
	//----------스케줄러----------	
	
	// 스케줄러 강아지 목록 출력
	@Query("select p from Puppy p join p.users u where u.uno = :uno")
	List<Puppy> findPuppyByUno(@Param("uno") int uno);


	
	@Query("select s from Schedule s where s.users.uno = :uno and s.s_date = :date")
	List<Schedule> findSchedulesByDate(@Param("uno") int uno, @Param("date") Date date);



}