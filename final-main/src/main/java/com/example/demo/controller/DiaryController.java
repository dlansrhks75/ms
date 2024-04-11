package com.example.demo.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.RegionCodeDAO;
import com.example.demo.dto.LoginFormDTO;
import com.example.demo.entity.Diary;
import com.example.demo.entity.Users;
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
    public String diaryWritePage(Model model) {
        model.addAttribute("diary", new Diary());
        return "member/diary/diaryWrite";
    }

    

    @PostMapping("/member/diary/insertDiary")
    public String insertDiary(@ModelAttribute Diary diary, @RequestParam("uploadFile") MultipartFile file) {
    	
    	// 현재 날짜를 지정
    	diary.setD_date(LocalDateTime.now());
    	
        // 파일 저장 경로 설정
    	String path = "/Users/sooyoung/git/ms/ms/final-main/src/main/resources/static/images";
        
        // 파일 업로드 처리
        if (!file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                Path filePath = Paths.get(path, fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                diary.setD_fname(fileName); // 파일명을 d_fname 필드에 설정
            } catch (IOException e) {
                e.printStackTrace(); // 실제 환경에서는 적절한 로깅 및 예외 처리 필요
            }
        }
        
        int no = ds.getNextDno();
        

        // Users 객체 생성 및 설정
        Users user = new Users();
        user.setUno(101); // 임시 사용자 ID 설정, 실제로는 세션 또는 인증 정보에서 가져올 값
        diary.setUsers(user); // Diary 객체에 Users 객체 설정
        
        // Diary 객체 저장
        ds.saveDiary(diary);

        // 저장 후 diary.html 페이지로 리다이렉션
        return "redirect:/member/diary/diary";
    }

    
    
    
    
//    @PostMapping("/insertDiary")
//    public String insertDiary(@ModelAttribute Diary diary, @RequestParam("file") MultipartFile file) {
//        ds.saveDiary(diary, file);
//        return "redirect:/member/diary/diary"; // 글 등록 후 리디렉션될 경로
//    }
    
    @GetMapping("/member/diary/diaryUpdate/{dno}")
    public String diaryUpdatePage(@PathVariable("dno") int dno, Model model) {
        Diary diary = ds.getDiaryById(dno);
        model.addAttribute("diary", diary);
        return "member/diary/diary"; 
    }
    
//    @PostMapping("/member/diary/updateDiary")
//    public String updateDiary(@ModelAttribute Diary diary, @RequestParam("file") MultipartFile file) {
//        ds.saveDiary(diary, file);
//        return "redirect:/member/diary/diary";
//    }
}
    