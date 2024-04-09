package com.example.demo.entity;

import java.sql.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name="diary")
public class Diary {
	
	@Id
	private int dno;
	
	private String d_title;
	private String d_content;
	private Date d_date;
	private String d_fname;
	
	@ManyToOne
	@JoinColumn(name="uno")
	private Users users; 
}
