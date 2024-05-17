package com.example.jobking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller

public class JController {

	
	@RequestMapping("/")
	public String root() {
		return "index";
	}
	
	@RequestMapping("/login_form")
	public void loginForm() {
		
	}
	@RequestMapping("/user_login")
	public void userLogin(HttpServletRequest request) {
//		String uid = request.getParameter("uid");
//		String upw = request.getParameter("upw");
//		System.out.println("xxx");
	}
	@RequestMapping("/company_login")
	public void companyLogin() {
		
	}
}
