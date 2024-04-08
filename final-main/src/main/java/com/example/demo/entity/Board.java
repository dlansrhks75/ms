package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name="board")
public class Board {

	@Id
	private int bno;
	
	@Id
	@ManyToOne
	@JoinColumn(name="b_code", insertable = true, updatable = true)
	private BoardCode boardCode;
	
	private String b_title;
	private String b_content;
	private String b_fname;
	private LocalDateTime b_date;
	private int b_price;
	private int b_hit;
	
	@ManyToOne
	@JoinColumn(name="uno", insertable = true, updatable = true)
	private Users users; 
	
	@ManyToOne
	@JoinColumn(name="rno", insertable = true, updatable = true)
	private RegionCode regionCode;
	
    @Transient //테이블에서 제외
	private MultipartFile uploadFile; 
}
