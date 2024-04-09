package com.example.demo.entity;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class Users {

    @Id
    private int uno;
    private String u_name;
    private String u_email;
    private String u_pwd;
    private String u_phone;
    private String u_nickname;
    private String u_fname;
 
    @ManyToOne
    @JoinColumn(name = "rno", referencedColumnName = "rno")
    private RegionCode regioncode;
    
    @Transient
    private MultipartFile uploadFile;
    
}