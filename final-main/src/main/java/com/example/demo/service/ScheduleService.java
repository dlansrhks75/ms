package com.example.demo.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PuppyDAO;
import com.example.demo.dao.ScheduleDAO;
import com.example.demo.dao.UsersDAO;
import com.example.demo.entity.Puppy;
import com.example.demo.entity.Schedule;
import com.example.demo.entity.Users;


@Service
public class ScheduleService {
	
	@Autowired
	private ScheduleDAO dao;
	@Autowired
    private UsersDAO usersDAO;
    @Autowired
    private PuppyDAO puppyDAO;
	
	
	//----------스케줄러----------
	public List<Puppy> getPuppyByUsersId(int uno) {
        return dao.findPuppyByUno(uno);
    }
	
	// 특정 날짜 스케줄 가져오기
	public List<Schedule> getSchedulesByDate(int uno, Date date) {
		  return dao.findSchedulesByDate(uno, date);
		}
	
    public List<Schedule> getMonthlySchedules(int uno, int year, int month) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth());
        return dao.findSchedulesByMonth(uno, Date.valueOf(startOfMonth), Date.valueOf(endOfMonth));
    }
    
	
    public void saveSchedule(Schedule schedule) {
        dao.save(schedule);
    }

	
    public void saveSchedule(Schedule schedule, Integer uno, Integer pno) {
        Users user = usersDAO.findById(uno).orElse(null);
        Puppy puppy = puppyDAO.findById(pno).orElse(null);

        if (user != null && puppy != null) {
            schedule.setUsers(user);
            schedule.setPuppy(puppy);

            java.util.Date utilDate = schedule.getS_date();
            if (utilDate != null) {
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                schedule.setS_date(sqlDate);            
            }

            dao.save(schedule);
        }
    }

	public int getNextSno() {
		return dao.getNextSno();
	}
	
	
}
