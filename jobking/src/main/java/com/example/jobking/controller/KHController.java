package com.example.jobking.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.jobking.entity.Career;
import com.example.jobking.entity.Experience;
import com.example.jobking.entity.Hope;
import com.example.jobking.entity.License;
import com.example.jobking.entity.Oa;
import com.example.jobking.entity.Resume;
import com.example.jobking.entity.School;
import com.example.jobking.entity.SelfInfo;
import com.example.jobking.entity.User;
import com.example.jobking.repository.ICareerRepository;
import com.example.jobking.repository.IExperienceRepository;
import com.example.jobking.repository.IHopeRepository;
import com.example.jobking.repository.ILicenseRepository;
import com.example.jobking.repository.IOaRepository;
import com.example.jobking.repository.IResumeRepository;
import com.example.jobking.repository.ISchoolRepository;
import com.example.jobking.repository.ISelfInfo;
import com.example.jobking.repository.IUserRepository;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/user")
@Controller
public class KHController {

	@Autowired
	IResumeRepository ResumeRepository;
	@Autowired
	ISelfInfo SelfInfoRepository;
	@Autowired
	IUserRepository UserRepository;
	@Autowired
	ICareerRepository CareerReopsitory;
	@Autowired
	IHopeRepository HopeReository;
	@Autowired
	IOaRepository OaRepository;
	@Autowired
	ISchoolRepository SchoolRepository;
	@Autowired
	ILicenseRepository LicenseRepository;
	@Autowired
	IExperienceRepository ExperienceRepository;
	
	
	
	@RequestMapping("/user_mainPage")
	public String mainPage() {

		return "user/user_mainPage";
	}


	@RequestMapping("/user_resumeList")
	public void resumeList(HttpServletRequest request, Model model) {
	    String uid = (String) request.getSession().getAttribute("id");

	    // 현재 사용자의 이력서만 가져오도록 수정
	    List<Resume> resumeList = ResumeRepository.findByUser_uid(uid);
	    List<SelfInfo> selfList = SelfInfoRepository.findByUser_uid(uid);

	    // SelfInfo를 Resume ID를 키로 하여 매핑
	    Map<Long, List<SelfInfo>> selfinfoMap = selfList.stream()
	        .collect(Collectors.groupingBy(selfInfo -> selfInfo.getResume().getRno()));

	    model.addAttribute("resumeList", resumeList);
	    model.addAttribute("selfinfoMap", selfinfoMap);
	    model.addAttribute("resumeCount", resumeList.size());
	}


	
	
	@RequestMapping("/user_resume_form")
	public void resumeForm(HttpServletRequest request, Model model) {
		
		String uid = (String) request.getSession().getAttribute("id");
				
				Optional<User> user = UserRepository.findById(uid);
				
				user.ifPresent(u->{
					model.addAttribute("user", u );
				});	
				
				Resume resume = new Resume();
				resume.setUser(user.get());
				
				
				Long rno = ResumeRepository.findlatestRno(uid);
				
				model.addAttribute("rno", rno);
	}
	
	@RequestMapping("/user_resume")
	public String resume(HttpServletRequest request, Resume resume, School school, Career career, Hope hope, SelfInfo selfInfo, Oa oa, License license, Experience experience) {
		System.out.println("컨트롤러 실행");
		System.out.println(request.getParameter("rno"));
		String uid = (String) request.getSession().getAttribute("id");
		
		User user = UserRepository.findById(uid).get();
		System.out.println(resume);
		school.setUser(user);
		career.setUser(user);
		hope.setUser(user);
		selfInfo.setUser(user);
		oa.setUser(user);
		license.setUser(user);
		experience.setUser(user);
		resume.setUser(user);
		school.setResume(resume);
		career.setResume(resume);
		hope.setResume(resume);
		selfInfo.setResume(resume);
		oa.setResume(resume);
		license.setResume(resume);
		experience.setResume(resume);
		
		System.out.println(school);
		System.out.println(career);
		System.out.println(hope);
		System.out.println(selfInfo);
		System.out.println(oa);
		System.out.println(license);
		System.out.println(experience);
		System.out.println(resume);
		
		ResumeRepository.save(resume);
		SelfInfoRepository.save(selfInfo);
		UserRepository.save(user);
		CareerReopsitory.save(career);
		HopeReository.save(hope);
		OaRepository.save(oa);
		SchoolRepository.save(school);
		LicenseRepository.save(license);
		ExperienceRepository.save(experience);
		
		return "redirect:user_resumeList";
	}
	
	@PostMapping("/setRepresentative")
	public String setRepresentative(@RequestBody Map<String, Long> payload) {
	    Long resumeId = payload.get("resumeId");
	    List<Resume> allResumes = ResumeRepository.findAll();
	    
	    // 모든 이력서의 def 필드를 "0"으로 설정
	    for (Resume resume : allResumes) {
	        resume.setDef("0");
	        ResumeRepository.save(resume);
	    }

	    // 선택된 이력서의 def 필드를 "1"으로 설정
	    Optional<Resume> optionalResume = ResumeRepository.findById(resumeId);
	    if (optionalResume.isPresent()) {
	        Resume resume = optionalResume.get();
	        resume.setDef("1"); // 대표 이력서로 설정
	        ResumeRepository.save(resume);
	    }
	    return "redirect:/user/user_resumeList";
	}

}
