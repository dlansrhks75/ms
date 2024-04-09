package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dao.RegionCodeDAO;
import com.example.demo.dto.LoginFormDTO;
import com.example.demo.entity.Diary;
import com.example.demo.service.DiaryService;
import com.example.demo.service.UsersService;

@Controller
public class DiaryController {
	
	@Autowired
	private DiaryService ds;
	
	//----------스케줄러----------
	
    @GetMapping("/member/diary/scheduler")
    public void scheduler() {
    } 
    @GetMapping("/member/diary/schedulerWrite")
    public void schedulerWritePage() {
    }
    
    
	//----------다이어리----------
    
    @GetMapping("/member/diary/diary")
    public String diaryPage(Model model) {
        int uno = 101; // 현재 로그인한 사용자의 uno 임시값
        List<Diary> diaries = ds.getDiariesByUno(uno);
        model.addAttribute("diaries", diaries);
        return "member/diary/diary";
    }
    
    // 특정 다이어리 상세
//    @GetMapping("/member/diary/diaryDetail/{id}")
//    public String diaryDetail(@PathVariable("id") int dno, Model model) {
//        Diary diary = ds.getDiaryById(dno);
//        model.addAttribute("diary", diary);
//        return "member/diary/diaryDetail";
//    }
    
    @GetMapping("/member/diary/diaryDetail/{dno}")
    public String diaryDetail(@PathVariable("dno") int dno, Model model) {
        Diary diary = ds.getDiaryById(dno);
        if (diary != null) {
            model.addAttribute("diary", diary);
            return "member/diary/diaryDetail";
        } else {
            return "redirect:/diaryPage"; // 적절한 에러 페이지 또는 리스트 페이지로 리다이렉트
        }
    }

    
    
    @GetMapping("/member/diary/diaryDetail")
    public void diaryDetailPage() {
    }
    @GetMapping("/member/diary/diaryWrite")
    public void diaryWritePage() {
    }
    @GetMapping("/member/diary/diaryUpdate")
    public void diaryUpdatePage() {
    }
}
    