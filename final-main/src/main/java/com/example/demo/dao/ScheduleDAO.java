package com.example.demo.dao;

import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Puppy;
import com.example.demo.entity.Schedule;

@Repository
public interface ScheduleDAO extends JpaRepository<Schedule, Integer> {
	
	//----------스케줄러----------	
	
	// 스케줄러 강아지 목록 출력
	@Query("select p from Puppy p join p.user u where u.uno = :uno")
	public List<Puppy> findPuppyByUno(@Param("uno") int uno);

	
	@Query("select s from Schedule s where s.users.uno = :uno and s.s_date = :date")
	public List<Schedule> findSchedulesByDate(@Param("uno") int uno, @Param("date") Date date);

	
	@Query("select s from Schedule s where s.users.uno = :uno and s.s_date between :startOfMonth and :endOfMonth")
	public List<Schedule> findSchedulesByMonth(@Param("uno") int uno, @Param("startOfMonth") Date startOfMonth, @Param("endOfMonth") Date endOfMonth);

	 
	@Query(value = "select ifnull(max(sno),0)+1 from schedule", nativeQuery = true)
	public int getNextSno();
	
	
	@Modifying
	@Query("update Schedule s set s.s_content = :content where s.sno = :sno")
	void updateContentById(@Param("sno") int id, @Param("content") String content);


	@Modifying
	@Query("update Schedule s set s.s_complete = :s_complete where s.sno = :sno")
	void updateScheduleStatus(@Param("sno") int sno, @Param("s_complete") String s_complete);

	
}
