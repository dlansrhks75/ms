package com.example.demo.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.OptionalInt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.LoginFormDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.BoardCode;
import com.example.demo.entity.BoardId;
import com.example.demo.entity.RegionCode;
import com.example.demo.entity.Users;
import com.example.demo.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsedgoodController {

	@Autowired
	private BoardService bs;

	// 중고거래 조회
	@GetMapping("/usedgood/usedgood")
	public void usedgoodPage(@RequestParam(required = false) String category,
	                         @RequestParam(required = false) String search,
	                         @RequestParam(required = false) String rno,
	                         @RequestParam(value = "page", defaultValue = "1") int page,
	                         Model model) {
	    int pageSize = 1;
	    int start = (page - 1) * pageSize; // SQL 쿼리에서는 0부터 시작하므로 page에서 0 빼기
	    
	    

	    Pageable pageable = PageRequest.of(page-1, pageSize);
	    Page<Board> list = bs.listUsedgood(6, pageable);
	    
	    int startPage = Math.max(1,list.getPageable().getPageNumber()-1);
	    int endPage = Math.min(list.getTotalPages(),list.getPageable().getPageNumber()+1);
	    model.addAttribute("startPage",startPage);
	    model.addAttribute("endPage",endPage);
	    
	    // 검색 관련
	    if (category != null && category.equals("b_title") && search != null) {
//	        model.addAttribute("list", bs.searchUsedgoodByTitle(6, search, start));
	        
	    } else if(category != null && category.equals("rno") && search != null){
//	    	model.addAttribute("list",bs.searchUsedgoodByTitleAndRegion(6, rno, search, start));
	    }else {
	        model.addAttribute("list", list);            
	    }
	}

	// 중고거래 상세
	@GetMapping("/member/usedgood/detail/{b_code}/{bno}")
	public String usedgoodDetailPage(@PathVariable int b_code, @PathVariable int bno, Model model) {
		model.addAttribute("b", bs.detailBoard(bno, b_code));
		bs.updateHit(bno, b_code);
		return "/member/usedgood/detail";
	}

	// 중고거래 글 등록 페이지
	@GetMapping("/member/usedgood/write")
	public void usedgoodWritePage() {
	}

	@Autowired //경로찾기용
	private ResourceLoader resourceLoader;

	// 중고거래 글 등록 insert
	@PostMapping("/member/usedgood/insert/{b_code}") //사실 여긴 b_code말고 걍 6 때려넣으면 되지만 딴데서 쓰려고 일단 일케 함
	public String usedgoodInsert(Board b, @PathVariable int b_code) {

		// 현재시간
		LocalDateTime now = LocalDateTime.now();

		// 게시판번호에 따른 게시글번호 증가
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

		// 파일 관련
		MultipartFile uploadFile = b.getUploadFile();
		String fname = b.getUploadFile().getOriginalFilename();
		String path = null;
//			String path = request.getServletContext().getRealPath("classpath:/static/images");실패
		Resource resource = resourceLoader.getResource("classpath:/static/images"); // 절대경로 찾기
		try {
			path = resource.getFile().getAbsolutePath();
			System.out.println(path);

		} catch (IOException e) {
			System.out.println("경로 가져오는 중 예외 발생:" + e);
		}

		if (fname != null && !fname.equals("")) {
			try {
				byte[] data = uploadFile.getBytes();
				FileOutputStream fos = new FileOutputStream(path + "/" + fname);
				fos.write(data);
				fos.close();
				b.setB_fname(fname);
				System.out.println(path);
			} catch (IOException e) {
				System.out.println("파일등록예외발생:" + e.getMessage());
			}
		}
		
		
		
		
		//테스트용
		System.out.println("b" + b);
		System.out.println("bno: " + b.getId().getBno());
		System.out.println("b_code: " + b.getId().getB_code());

		bs.insertUsedgood(b);
		return "redirect:/usedgood/usedgood";
	}
}
