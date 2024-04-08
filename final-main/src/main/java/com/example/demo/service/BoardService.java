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

@Service
public class BoardService {

   @Autowired
   private BoardDAO dao;
   
   public List<Object[]> listUsedGood(){
	   return dao.findBoardByBCode(6, 0);
   }
}