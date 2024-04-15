package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.LoginFormDTO;
import com.example.demo.entity.Board;
import com.example.demo.service.BoardService;
import com.example.demo.service.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CommunityController {
	
	@Autowired
	private BoardService bs;
	
	//----------게시판형(자유,질문,모임)----------
	@GetMapping("/community/board")
	public void boardPage(String b_name,
						            HttpSession session,
						            Model model) {
		model.addAttribute("b_name", b_name);
	}
	
	
	
	
	//-----------사진형--------------
	//전국댕댕자랑 조회
	@GetMapping("/community/boast")
    public void boastPage() {
    }
	
	//신고제보 조회
    @GetMapping("/community/report")
    public void reportPage() {
    }
   
    //전국댕댕자랑 상세
    @GetMapping("/member/community/boastDetail")
    public void boastDetailPage() {
    }
    
    //신고제보 상세
    @GetMapping("/member/community/reportDetail")
    public void reportDetail() {
    }
    
    
    
    //---------게시판형, 사진형 공통---------------
    //글 작성
    @GetMapping("/member/community/boardWrite")
    public void boardWritePage(String b_name, HttpSession session, Model model) {
    	model.addAttribute("b_name", b_name);
    }
    
}
    
