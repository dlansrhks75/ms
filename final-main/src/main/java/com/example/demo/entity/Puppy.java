package com.example.demo.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name="puppy")
public class Puppy {

	@Id
	private int pno;
	private String p_name;
	private LocalDateTime p_birth;
	private String p_fname;
	private String p_sex;
	private String p_neuter;
	
	@ManyToOne
	@JoinColumn(name="uno", referencedColumnName="uno")
	private Users user;
	
	@Transient
	private MultipartFile uploadFile;
}
