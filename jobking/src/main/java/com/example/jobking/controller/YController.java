package com.example.jobking.controller;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.jobking.entity.Company;
import com.example.jobking.entity.User;
import com.example.jobking.repository.ICompanyRepository;
import com.example.jobking.repository.IUserRepository;

@RequestMapping("/user")
@Controller
public class YController {

	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	ICompanyRepository companyRepository;
	
	@RequestMapping("/user_regForm")
	public String regForm() {
		return "user_regForm";
	}
	
	// User 아이디 중복체크
	@RequestMapping("/uidCheck")
	public @ResponseBody String uidCheck(@RequestParam("uid") String uid) {
		Optional<User> result = userRepository.findById(uid);
		String msg = "";
		System.out.println(result);
		if(result.isPresent()) {
			msg = "중복된 아이디 입니다.";
		}else {
			msg = "사용가능한 아이디 입니다.";
		}
		return msg;
	}
	
	
	// Company 아이디 중복체크
	@RequestMapping("/cidCheck")
	public @ResponseBody String cidCheck(@RequestParam("cid") String cid) {
		Optional<Company> result = companyRepository.findById(cid);
		String msg = "";
		System.out.println(result);
		if(result.isPresent()) {
			msg = "중복된 아이디 입니다.";
		}else {
			msg = "사용가능한 아이디 입니다.";
		}
		return msg;
	}
	
	// 
	@RequestMapping("/user_regist")
	public String userReg(User user) {
		Optional<User> result = userRepository.findById(user.getUid());
		if(result.isPresent()) {
			return "user_regForm";
		}else {
			userRepository.save(user);
		}
		return "login_form";
	}
	

	@RequestMapping("/com_regist")
	public String comReg(Company company) {
		Optional<Company> result = companyRepository.findById(company.getCid());
		if(result.isPresent()) {
			return "user_regForm";
		}else {
			companyRepository.save(company);
		}
		return "login_form";
	}
	
	
}
