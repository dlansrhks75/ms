package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class BoardId implements Serializable{

	private int bno;
	private int b_code;
}
