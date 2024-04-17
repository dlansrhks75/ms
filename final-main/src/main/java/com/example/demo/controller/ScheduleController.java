package com.example.demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Diary;
import com.example.demo.entity.Puppy;
import com.example.demo.entity.Schedule;
import com.example.demo.entity.Users;
import com.example.demo.service.DiaryService;
import com.example.demo.service.ScheduleService;
import com.example.demo.service.UsersService;

@Controller 
public class ScheduleController {
	
	
	//----------스케줄러----------

	
	@Autowired
	private ScheduleService ss;
	
	
    @GetMapping("/member/diary/scheduler")
    public String scheduler(Model model, @RequestParam(defaultValue = "101") int uno) {
        List<Puppy> puppies = ss.getPuppyByUsersId(uno);
        model.addAttribute("puppies", puppies);
        return "member/diary/scheduler";      }
    
    

    @GetMapping("/get-schedule")
    @ResponseBody
    public List<Schedule> getSchedules(@RequestParam int uno,
                                       @RequestParam int year,
                                       @RequestParam int month,
                                       @RequestParam(required = false) Integer day) {
        // month 0부터 시작(+1)
        month += 1;
        if (day != null) {
            // 일별 스케줄을 요청
            LocalDate date = LocalDate.of(year, month, day);
            return ss.getSchedulesByDate(uno, Date.valueOf(date));
        } else {
            // 월별 스케줄을 요청
            return ss.getMonthlySchedules(uno, year, month);
        }
    }
    
    // 스케줄러 등록
    @GetMapping("/member/diary/schedulerWrite")
    public String schedulerWrite(Model model, @RequestParam(defaultValue = "101") int uno) {
        List<Puppy> puppies = ss.getPuppyByUsersId(uno);
        model.addAttribute("puppies", puppies);
        return "member/diary/schedulerWrite";
    }
    
    
    @PostMapping("/schedule/save")
    public String saveSchedule(@ModelAttribute Schedule schedule,
                               @RequestParam("pno") int pno,
                               @RequestParam("uno") int uno,
                               Model model) {
    	int nextSno = ss.getNextSno();
        schedule.setSno(nextSno);
        ss.saveSchedule(schedule, uno, pno);  // 일정 저장
        return "redirect:/member/diary/scheduler";          // 저장 후 리디렉션
    }
    
    
    // 내용 수정하기
    @PostMapping("/update-schedule")
    public String updateSchedule(@RequestParam("sno") int sno, @RequestParam("s_content") String sContent) {
        ss.updateSchedule(sno, sContent);
        return "redirect:/member/diary/scheduler"; // 폼 재전송 방지를 위해 리디렉션
    }
    
    // 내용 삭제하기
    @PostMapping("/schedule/delete")
    public @ResponseBody String deleteSchedule(@RequestParam("sno") int sno) {
        try {
            ss.deleteSchedule(sno);
            return "삭제되었습니다.";
        } catch (Exception e) {
            return "삭제 실패하였습니다.";
        }
    }

    @PostMapping("/schedule/update-status")
    @ResponseBody
    public String updateScheduleStatus(@RequestParam("sno") int sno, @RequestParam("s_complete") String sComplete) {
        ss.updateScheduleStatus(sno, sComplete);
        return "redirect:/member/diary/scheduler";
    }


}
    