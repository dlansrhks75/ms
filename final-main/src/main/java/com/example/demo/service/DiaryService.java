package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DiaryDAO;
import com.example.demo.entity.Diary;


@Service
public class DiaryService {
	
	@Autowired
	private DiaryDAO dao;
	

	
	// 유저별 전체 다이어리 조회
	public List<Diary> getDiariesByUno(int uno) {
	    return dao.findByUsersUno(uno);
	}
	
	// 일지 상세 정보
	public List<Diary> getDiaryDetailsByUnoAndDno(int uno, int dno){
		return dao.findByUsersUnoAndDno(uno, dno);
	}
	
	// 일지 상세
	public Diary getDiaryById(int dno) {
		return dao.findById(dno).orElse(null);
    }
	
	
}
