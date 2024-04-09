package com.example.demo.dao;

import com.example.demo.entity.Board;
import com.example.demo.entity.Users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardDAO extends JpaRepository<Board, Integer> {
	
	//중고거래(사진형게시판) 16개씩 조회(페이징용)
    @Query(value = "SELECT * FROM board WHERE b_code = ?1 ORDER BY b_date DESC LIMIT 16 OFFSET ?2", nativeQuery = true)
    public List<Board> findBoardByBCode(int bCode, int start); //start는 임시
    
    //getNextBno(게시판 구분해서)
    @Query(value ="select ifnull(max(bno),0)+1 from board where b_code = ?", nativeQuery = true)
    public int getNextBno(int b_code);
}
