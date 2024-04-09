package com.example.demo.controller;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.RegionCodeDAO;
import com.example.demo.entity.Users;
import com.example.demo.service.UsersService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MypageController {
	
	@Autowired
	private UsersService us;
	
	@Autowired
	private RegionCodeDAO dao;
	
	@Autowired
	private ResourceLoader resourceLoader;

	@GetMapping("/member/mypage/changeInfo")
    public void changeInfoPage(Model model) {
		model.addAttribute("u",us.findById());
		model.addAttribute("region",dao.findAll());
    }
	
	@PostMapping("/member/mypage/changeInfo")
	public String changeInfo(Users u, int rno, HttpServletRequest request) {
		String viewPage = "redirect:/member/mypage/changeInfo";
	    String oldFname = u.getU_fname();
	    Resource resource = resourceLoader.getResource("classpath:/static/images"); //절대경로 찾기
	    String fname = null;
	    String path = null;
	    MultipartFile uploadFile = u.getUploadFile();

	    // uploadFile이 null인지 확인
	    if (uploadFile != null) {
	        fname = uploadFile.getOriginalFilename();
	        if (fname != null && !fname.equals("")) {
	            try {
	            	path = resource.getFile().getAbsolutePath();
	                System.out.println("이미지 경로 : "+path);
	                FileOutputStream fos = new FileOutputStream(path + "/" + fname);
	                FileCopyUtils.copy(uploadFile.getBytes(), fos);
	                fos.close();
	                u.setU_fname(fname);
	            } catch (Exception e) {
	            	System.out.println("예외발생 : "+e.getMessage());
	            }
	        }
	    }
	    
	    u.setRegioncode(dao.findById(rno).orElse(null));
	    int re = us.updateInfo(u.getU_name(), u.getU_email(), u.getU_phone(), u.getU_nickname(), u.getU_fname(), u.getUno());
	    if(re == 1) {
	    	if(fname != null && !fname.equals("") && oldFname != null && !oldFname.equals("")) {
				File file = new File(path + "/"+oldFname);
				file.delete();
			}
	    }else {
	    	System.out.println("게시물 수정에 실패했습니다.");
	    }
	    System.out.println(u);
	    System.out.println(rno);
	    return viewPage;
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
    
