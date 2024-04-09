package com.example.demo.service;

import com.example.demo.dao.BoardDAO;
import com.example.demo.entity.Board;
import com.example.demo.entity.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {

   @Autowired
   private BoardDAO dao;
   
   public List<Board> listUsedGood(){
	   return dao.findBoardByBCode(6, 0);
   }
   
   //일단 중고장터용 getNextNo
   public int getNextUsedgoodBno() {
	   return dao.getNextBno(6);
   }
   
   //중고장터 게시글 insert
   public void insertUsedgood(Board b) {
	   dao.save(b);
   }
   
   //게시글 상세
   public Map<String,Object> detailBoard(int bno, int b_code) {
	   System.out.println(dao.detailBoard(bno, b_code));
	   return dao.detailBoard(bno, b_code);
   }
}