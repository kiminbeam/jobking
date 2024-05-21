package com.example.jobking.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.jobking.entity.Resume;
import com.example.jobking.entity.User;
import com.example.jobking.repository.ICompanyRepository;
import com.example.jobking.repository.IJobAdRepository;
import com.example.jobking.repository.IResumeRepository;
import com.example.jobking.repository.IUserRepository;

@RequestMapping("/company")
@Controller
public class NCompanyController {

	
	@Autowired
	IJobAdRepository repository;

	@Autowired
	ICompanyRepository comrepository;

	@Autowired
    private IUserRepository userRepository;

    @Autowired
    private IResumeRepository resumeRepository;
	
	
	@RequestMapping("com_totalfind")
	public String totalFind(Model model) {
		
		List<User> allUsers = userRepository.findAll(); // 모든 유저 조회
        List<Resume> defaultResumes = new ArrayList<>();

        for (User user : allUsers) {
            Resume defaultResume = resumeRepository.findByUserAndDef(user, "1");
            if (defaultResume != null) {
                defaultResumes.add(defaultResume); // 대표 이력서만 추가
            }
        }
        
        long defaultResumeCount = resumeRepository.countByDef("1"); // 대표 이력서 갯수 조회
        model.addAttribute("defaultResumeCount", defaultResumeCount);

        model.addAttribute("defaultResumes", defaultResumes);
		
		
		return "company/com_totalfind";
	}
	
	
	
	
}
