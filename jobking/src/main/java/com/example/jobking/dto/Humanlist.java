package com.example.jobking.dto;

import lombok.Data;

@Data
public class Humanlist {
	private String title;
	private String username;
	private String userid;
	
	public Humanlist(String title, String username,String userid) {
		this.title=title;
		this.username= username;
		this.userid = userid;
	}
}
