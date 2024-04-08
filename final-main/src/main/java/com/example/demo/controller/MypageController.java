package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dao.RegionCodeDAO;
import com.example.demo.dto.LoginFormDTO;
import com.example.demo.service.UsersService;

@Controller
public class MypageController {
	
	@Autowired
	private UsersService us;
	
	@Autowired
	private RegionCodeDAO dao;

	@GetMapping("/member/mypage/changeInfo")
    public void changeInfoPage(Model model) {
		model.addAttribute("u",us.findById());
		model.addAttribute("region",dao.findAll());
		System.out.println(dao.findAll());
    }
    @GetMapping("/member/mypage/changePwd")
    public void changePwdPage() {
    }
    @GetMapping("/member/mypage/insertPuppy")
    public void insertPuppyPage() {
    }
    @GetMapping("/member/mypage/listPuppy")
    public void listPuppyForm(Model model) {
    }
    @GetMapping("/member/mypage/myPosts")
    public void myPostsPage() {
    }
    
}
    
