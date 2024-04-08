package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class BoardId implements Serializable{

	private int bno;
	private int b_code;
}
