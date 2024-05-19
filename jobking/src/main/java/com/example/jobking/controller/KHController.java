package com.example.jobking.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.jobking.entity.Resume;
import com.example.jobking.entity.SelfInfo;
import com.example.jobking.entity.User;
import com.example.jobking.repository.IResumeRepository;
import com.example.jobking.repository.ISelfInfo;
import com.example.jobking.repository.IUserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class KHController {

	@Autowired
	IResumeRepository ResumeRepository;
	@Autowired
	ISelfInfo SelfInfoRepository;
	@Autowired
	IUserRepository UserRepository;
	
	
	@RequestMapping("/user_mainPage")
	public String mainPage() {

		return "user_mainPage";
	}

	
	@RequestMapping("/user_resumeList")
	public void resumeList(Model model) {
	    List<Resume> resumeList = ResumeRepository.findAll();
	    List<SelfInfo> selfList = SelfInfoRepository.findAll();

	    // SelfInfo를 Resume ID를 키로 하여 매핑
	    Map<Long, SelfInfo> selfinfoMap = selfList.stream()
	        .collect(Collectors.toMap(
	            selfInfo -> selfInfo.getResume().getRno(),
	            Function.identity(),
	            (existing, replacement) -> existing  // 키가 중복될 경우 기존 값을 유지
	        ));

	    model.addAttribute("resumeList", resumeList);
	    model.addAttribute("selfinfoMap", selfinfoMap);
	}
	
	
	@RequestMapping("/user_resume_form")
	public String resumeForm(HttpServletRequest request, Model model) {
		
//		String uid = (String) request.getSession().getAttribute("id");
//				
//				Optional<User> user = UserRepository.findById(uid);
//				user.ifPresent(u->{
//					model.addAttribute("user", u );
//				});	
		
		return "user_resume_form";
	}
	
	@RequestMapping("/user_resume")
	public String resume() {
		
			
		
		return "redirect:user_resumeList";
	}
}
