package com.example.jobking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.jobking.repository.IAbgLoginTimeRepository;
import com.example.jobking.repository.IQnaTableRepository;
import com.example.jobking.repository.IUserRepository;

@RequestMapping("/manager")
@Controller
public class ManController {
	
	@Autowired
	IUserRepository userRepository;
	@Autowired
	IAbgLoginTimeRepository abgLoginTimeRepository;
	@Autowired
	IQnaTableRepository qnaTableRepository;
	
	@RequestMapping("/dashboard")
	public void dashboard(Model model) {
	
		// 유저 회원 가입 수
		long userCount = userRepository.count();
        model.addAttribute("userCount", userCount);
		
        // 2. 문의 수
        long userInquiryCount = qnaTableRepository.countByUserUidIsNotNull();
        long companyInquiryCount = qnaTableRepository.countByCompanyCidIsNotNull();

        model.addAttribute("userInquiryCount", userInquiryCount);
        model.addAttribute("companyInquiryCount", companyInquiryCount);
        
        
	}
	
}
