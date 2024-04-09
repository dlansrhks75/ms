package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.OptionalInt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.LoginFormDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.BoardCode;
import com.example.demo.entity.BoardId;
import com.example.demo.entity.RegionCode;
import com.example.demo.entity.Users;
import com.example.demo.service.BoardService;

@Controller
public class UsedgoodController {
	
	@Autowired
	private BoardService bs;
	
	 //중고거래 조회
	 @GetMapping("/usedgood/usedgood")
	    public void usedgoodPage(Model model) {
		 	model.addAttribute("list",bs.listUsedGood());
	    }
    
	 //중고거래 상세
	 @GetMapping("/member/usedgood/detail")
	    public void usedgoodDetailPage() {
	    }
	 
	 //중고거래 글 등록
	 @GetMapping("/member/usedgood/write")
	    public void usedgoodWritePage() {
	    }
	 
	 //중고거래 글 등록 insert
	 @PostMapping("/member/usedgood/insert/{b_code}")
	 public String usedgoodInsert(Board b, @PathVariable int b_code) {

			// 현재시간
			LocalDateTime now = LocalDateTime.now();
			
			//게시판번호에 따른 게시글번호 증가
			int bno = bs.getNextUsedgoodBno();

			// RegionCode 객체 생성 및 설정
			RegionCode regionCode = new RegionCode();
			regionCode.setRno(b.getRegionCode().getRno());
			b.setRegionCode(regionCode);

			// BoardCode 객체 생성 및 설정
			if (b.getBoardcode() == null) {
				b.setBoardcode(new BoardCode());
			}
			b.getBoardcode().setB_code(b_code);

			// 복합키 처리
			BoardId boardId = new BoardId();
			boardId.setB_code(b_code);
			boardId.setBno(bno);
			b.setId(boardId);
			
			b.setB_date(now);

			// 로그인 세션유지 하게 되면 이거 세션에서 아이디 가져와서 하는 걸로 수정해야됨!! 일단 101로 넣어놓음
			int userId = 101;
			if (b.getUsers() == null) {
				b.setUsers(new Users());
			}
			b.getUsers().setUno(userId);

			System.out.println("b" + b);
			System.out.println("bno: " + b.getId().getBno());
			System.out.println("b_code: " + b.getId().getB_code());
			
			bs.insertUsedgood(b);
			return "redirect:/usedgood/usedgood";
		}
}
    
