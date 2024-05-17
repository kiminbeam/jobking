package com.example.jobking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String userLogin(HttpServletRequest request, Model model) {
		String uid = request.getParameter("uid");
		String upw = request.getParameter("upw");
	
		//db에서 user 로그인 정보 확인
		//맞으면 result 보내주기
	
		return "redirect:/";
	}
	@RequestMapping("/company_login")
	public String companyLogin(HttpServletRequest request, Model model) {
		String cid = request.getParameter("cid");
		String cpw = request.getParameter("cpw");
		System.out.println(cid);
		//db에서 user 로그인 정보 확인
	    //맞으면 result 보내주기
		return "redirect:/";
	}
	@RequestMapping("/user_myPage")
	public void userMyPage(Model model) {
		
		model.addAttribute("name", "jia");
		
	}
}
