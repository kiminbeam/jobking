package com.example.jobking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CompanyController {
	
	@RequestMapping("/")
	public String main() {
		return "cpmain";
	}
	
	@RequestMapping("/regi_jobadForm")
	public String regiForm() {
		return "regi_jobadForm";
	}
	
	@RequestMapping("regi_jobad")
	public String regiAD() {
		//데이터 등록하는 메서드
		
		//등록한 채용공고 목록페이지
		return "redirect: jobadList";
	}
	
}
