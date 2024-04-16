package com.example.demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.RegionCodeDAO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Puppy;
import com.example.demo.entity.Users;
import com.example.demo.service.BoardCodeService;
import com.example.demo.service.BoardService;
import com.example.demo.service.PuppyService;
import com.example.demo.service.UsersService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MypageController {
	
	@Autowired
	private UsersService us;
	
	@Autowired
	private RegionCodeDAO rd;
	
	@Autowired
	private PuppyService ps;
	
	@Autowired
	private BoardService bs;
	
	@Autowired
	private BoardCodeService bcs;
	
	@Autowired
	private ResourceLoader resourceLoader;

	@GetMapping("/member/mypage/changeInfo")
    public void changeInfoPage(Model model) {
		int uno = 101;
		model.addAttribute("u",us.findById(uno));
		model.addAttribute("region",rd.findAll());
    }
	
	@PostMapping("/member/mypage/changeInfo")
	public String changeInfo(Users u, String rno, HttpServletRequest request) {
		int uno = 101;
		u.setUno(uno);
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
	    int re = us.updateInfo(u.getU_name(), u.getU_email(), u.getU_phone(), u.getU_nickname(), u.getU_fname(), rno, u.getUno());
	    if(re == 1) {
	    	if(fname != null && !fname.equals("") && oldFname != null && !oldFname.equals("")) {
				File file = new File(path + "/"+oldFname);
				file.delete();
			}
	    }else {
	    	System.out.println("게시물 수정에 실패했습니다.");
	    }
	    return viewPage;
	}

	
    @GetMapping("/member/mypage/changePwd")
    public void changePwdPage(Model model) {
    	int uno = 101;
    	String oldPwd = us.findById(uno).getU_pwd();
    	model.addAttribute("oldPwd", oldPwd);
    }
    @PostMapping("/member/mypage/changePwd")
    public String changePwd(String newPassword) {
    	String viewPage = "redirect:/member/mypage/changePwd";
    	int uno =101;
    	us.updatePwd(newPassword, uno);
    	return viewPage;
    }
    
    @PostMapping("/check-password")
    @ResponseBody
    public boolean checkPassword(String u_pwd) {
    	int uno =101;
        String dbPwd = us.findById(uno).getU_pwd();
        return u_pwd.equals(dbPwd);
    }
    
    @GetMapping("/member/mypage/listPuppy")
    public void listPuppyForm(Model model) {
    	int uno = 101;
    	List<Puppy> puppy = ps.findByUno(uno);
    	model.addAttribute("puppy", puppy);
    }
      
    @GetMapping("/member/mypage/insertPuppy")
    public void insertPuppyPage() {
    }
    @PostMapping("/member/mypage/insertPuppy")
    public String insertPuppy(Puppy p,HttpServletRequest request,Model model) {
    	int uno = 101;
    	p.setUser(us.findById(uno));
    	List<Puppy> puppy = ps.findByUno(uno);
    	model.addAttribute("puppy", puppy);
    	String viewPage = "redirect:/member/mypage/listPuppy";
    	
    	p.setPno(ps.getNextPno());
    	Resource resource = resourceLoader.getResource("classpath:/static/images"); //절대경로 찾기

    	MultipartFile uploFile = p.getUploadFile();
    	String fname = uploFile.getOriginalFilename();
    	p.setP_fname(fname); 
    	Puppy insertCheck = ps.insert(p);
    	
        	if(insertCheck != null && fname != null && !fname.equals("")) {
        		try {
        			String path = resource.getFile().getAbsolutePath();
        	    	System.out.println("이미지 경로 : "+path);   			  			
        			byte[]data = uploFile.getBytes();
        			FileOutputStream fos = new FileOutputStream(path+"/"+fname);
        			fos.write(data);
        			fos.close();
        		}catch (Exception e) {
    				System.out.println("예외발생 : "+e.getMessage());
    			}   		
        	}
    	return viewPage;
    }

    @GetMapping("/member/mypage/myPosts")
    public void myPostsPage(Model model) {
    	int uno = 101;
    	List<Board> boards = bs.findByUno(uno);
    	List<String> boardNames = new ArrayList<>();
    	List<String> formattedDates = new ArrayList<>(); // 변경된 날짜 형식을 담을 리스트
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    	
    	for(Board board : boards) {
    		String b_name = bcs.findById(board.getId().getB_code());
    		boardNames.add(b_name);
    		
    		 // 게시물의 작성 날짜를 가져와서 원하는 형식으로 변환
            LocalDateTime createDate = board.getB_date();
            String formattedDate = createDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")); // DateTimeFormatter 클래스의 ofPattern 메서드를 사용하여 원하는 날짜 및 시간 형식을 지정 
            formattedDates.add(formattedDate); // 변환된 날짜를 리스트에 추가
    	}
    	
    	model.addAttribute("boards", boards);
    	model.addAttribute("boardNames", boardNames);
    	model.addAttribute("formattedDates", formattedDates);
    }
    
}
    
