package com.example.jobking.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	ICareerRepository CareerRepository;
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
		List<Resume> resumeList = ResumeRepository.findByUid(uid);
		List<SelfInfo> selfList = SelfInfoRepository.findByUser_uid(uid);

		// SelfInfo를 Resume ID를 키로 하여 매핑
		Map<Long, List<SelfInfo>> selfinfoMap = selfList.stream()
				.collect(Collectors.groupingBy(selfInfo -> selfInfo.getResume().getRno()));

		model.addAttribute("resumeList", resumeList);
		model.addAttribute("selfinfoMap", selfinfoMap);
		model.addAttribute("resumeCount", resumeList.size());
	}

	@GetMapping("/user_resume_form")
	public void resumeForm(HttpServletRequest request, Model model) {
		String uid = (String) request.getSession().getAttribute("id");
		Optional<User> user = UserRepository.findById(uid);
		user.ifPresent(u -> model.addAttribute("user", u));
	}

//	@PostMapping("/user_resume_form")
//	public String createResume(HttpServletRequest request) {
//		String uid = (String) request.getSession().getAttribute("id");
//		Optional<User> user = UserRepository.findById(uid);
//		if (user.isPresent()) {
//			Resume resume = new Resume();
//			resume.setUser(user.get());
//			ResumeRepository.save(resume);
//		}
//		return "redirect:user_resumeList";
//	}

	@PostMapping("/user_resume")
	public String resume(HttpServletRequest request, Resume resume, School school, Career career, Hope hope,
			SelfInfo selfInfo, Oa oa, License license, Experience experience) {

		String uid = (String) request.getSession().getAttribute("id");
		
		User user = UserRepository.findById(uid).get();

		// 해당 아이디의 이력서가 이미 존재하는지 확인
		List<Resume> resumeExists = ResumeRepository.findByUid(uid);

		
		if (resumeExists.isEmpty()) {
			resume.setDef("1");
		}

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

		ResumeRepository.save(resume);

		if (school != null && school.getEduName() != null && !school.getEduName().trim().isEmpty()) {
			school.setUser(user);
			school.setResume(resume);
			SchoolRepository.save(school);
		}

		if (career != null && career.getCName() != null && !career.getCName().trim().isEmpty()) {
			career.setUser(user);
			career.setResume(resume);
			CareerRepository.save(career);
		}

		if (hope != null && hope.getRegion1() != null && !hope.getRegion1().trim().isEmpty()) {
			hope.setUser(user);
			hope.setResume(resume);
			HopeReository.save(hope);
		}

		if (selfInfo != null && selfInfo.getTitle() != null && !selfInfo.getTitle().trim().isEmpty()) {
			selfInfo.setUser(user);
			selfInfo.setResume(resume);
			SelfInfoRepository.save(selfInfo);
		}

		if (oa != null && oa.getContent() != null && !oa.getContent().trim().isEmpty()) {
			oa.setUser(user);
			oa.setResume(resume);
			OaRepository.save(oa);
		}

		if (license != null && license.getLname() != null && !license.getLname().trim().isEmpty()) {
			license.setUser(user);
			license.setResume(resume);
			LicenseRepository.save(license);
		}

		if (experience != null && experience.getOrg() != null && !experience.getOrg().trim().isEmpty()) {
			experience.setUser(user);
			experience.setResume(resume);
			ExperienceRepository.save(experience);
		}

		return "redirect:user_resumeList";
	}

	@PostMapping("/setRepresentative")
	public String setRepresentative(HttpServletRequest request, @RequestBody Map<String, Long> payload) {
		String uid = (String) request.getSession().getAttribute("id");
		Long resumeId = payload.get("resumeId");

		// 현재 사용자의 모든 이력서 가져오기
		List<Resume> userResumes = ResumeRepository.findByUid(uid);

		// 사용자의 모든 이력서의 def 필드를 "0"으로 설정
		for (Resume resume : userResumes) {
			resume.setDef("0");
			ResumeRepository.save(resume);
		}

		// 선택된 이력서의 def 필드를 "1"으로 설정
		Optional<Resume> optionalResume = ResumeRepository.findById(resumeId);
		if (optionalResume.isPresent()) {
			Resume resume = optionalResume.get();
			if (resume.getUser().getUid().equals(uid)) {
				resume.setDef("1");
				ResumeRepository.save(resume);
			}
		}

		return "redirect:/user/user_resumeList";
	}

	@GetMapping("/user_resume_detail")
	public String userResumeDetail(HttpServletRequest request, Model model, @RequestParam("rno") Long rno) {
		String uid = (String) request.getSession().getAttribute("id");

		Optional<Resume> resumeOpt = ResumeRepository.findResumeWithUserById(uid, rno);
		if (resumeOpt.isPresent()) {
			Resume resume = resumeOpt.get();
			model.addAttribute("resume", resume);

			User user = resume.getUser();
			model.addAttribute("user", user);

			List<Career> careerList = CareerRepository.findByResumeRno(rno);
			model.addAttribute("careerList", careerList);

			List<Experience> experienceList = ExperienceRepository.findByResumeRno(rno);
			model.addAttribute("experienceList", experienceList);

			List<Hope> hopeList = HopeReository.findByResumeRno(rno);
			model.addAttribute("hopeList", hopeList);

			List<License> licenseList = LicenseRepository.findByResumeRno(rno);
			model.addAttribute("licenseList", licenseList);

			List<Oa> oaList = OaRepository.findByResumeRno(rno);
			model.addAttribute("oaList", oaList);

			List<SelfInfo> selfInfoList = SelfInfoRepository.findByResumeRno(rno);
			model.addAttribute("selfInfoList", selfInfoList);

			List<School> schoolList = SchoolRepository.findByResumeRno(rno);
			model.addAttribute("schoolList", schoolList);

			// Add disclo value to model
			String disclo = resume.getDisclo();
			model.addAttribute("disclo", disclo);
		}
		return "/user/user_resume_detail";
	}

	@RequestMapping("/user_resume_edit")
	public String userResumeEdit(HttpServletRequest request, Model model, @RequestParam("rno") Long rno) {
		String uid = (String) request.getSession().getAttribute("id");

		Optional<Resume> resumeOpt = ResumeRepository.findResumeWithUserById(uid, rno);
		if (resumeOpt.isPresent()) {
			Resume resume = resumeOpt.get();
			model.addAttribute("resume", resume);

			User user = resume.getUser();
			model.addAttribute("user", user);

			List<Career> careerList = CareerRepository.findByResumeRno(rno);
			model.addAttribute("careerList", careerList);

			List<Experience> experienceList = ExperienceRepository.findByResumeRno(rno);
			model.addAttribute("experienceList", experienceList);

			List<Hope> hopeList = HopeReository.findByResumeRno(rno);
			model.addAttribute("hopeList", hopeList);

			List<License> licenseList = LicenseRepository.findByResumeRno(rno);
			model.addAttribute("licenseList", licenseList);

			List<Oa> oaList = OaRepository.findByResumeRno(rno);
			model.addAttribute("oaList", oaList);

			List<SelfInfo> selfInfoList = SelfInfoRepository.findByResumeRno(rno);
			model.addAttribute("selfInfoList", selfInfoList);

			List<School> schoolList = SchoolRepository.findByResumeRno(rno);
			model.addAttribute("schoolList", schoolList);
			
			
		}

		return "/user/user_resume_edit";
	}

	@PostMapping("/user_regupdate")
	public String update(HttpServletRequest request, Model model, 
			@RequestParam("rno") Long rno,
			@RequestParam("disclo") String disclo,
			@RequestParam(value = "sno", required = false) Long sno,
			@RequestParam(value = "hno", required = false) Long hno,
			@RequestParam(value = "ono", required = false) Long ono,
			@RequestParam(value = "sino", required = false) Long sino,
			@RequestParam(value = "cno", required = false) Long cno,
			@RequestParam(value = "lno", required = false) Long lno,
			@RequestParam(value = "eno", required = false) Long eno, Resume resume, School school, Career career,
			Hope hope, SelfInfo selfInfo, Oa oa, License license, Experience experience, 
			@ModelAttribute("school") List<School> schoolList, 
			@ModelAttribute("career") List<Career> careerList,
			@ModelAttribute("experience") List<Experience> experienceList,
			@ModelAttribute("hope") List<Hope> hopeList,
			@ModelAttribute("license") List<License> licenseList,
			@ModelAttribute("oa") List<Oa> oaList,
			@ModelAttribute("selfInfo") List<SelfInfo> selfInfoList
			) {
		String uid = (String) request.getSession().getAttribute("id");
		User user = UserRepository.findById(uid).get();
		resume = ResumeRepository.findById(rno).orElseThrow(); 

		resume.setDisclo(disclo); // disclo 값 설정
	    
		
		resume.setRno(rno);
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

		for (School school1 : schoolList) {
		    if (school1.getSno() != null) {
		        School existingSchool = SchoolRepository.findById(school1.getSno()).orElseThrow();
		        existingSchool.update(school1); // School 엔티티의 update 메서드 호출
		        SchoolRepository.save(existingSchool);
		    } else {
		        // ... (새로운 School 생성 로직)
		    }
		}
		
		for (Career career1 : careerList) {
		    if (career1.getCno() != null) {
		        Career existingCareer = CareerRepository.findById(career1.getCno()).orElseThrow();
		        existingCareer.update(career1); 
		        CareerRepository.save(existingCareer);
		    } else {
		        // ... (새로운 Career 생성 로직)
		    }
		}

		for (Experience experience1 : experienceList) {
		    if (experience1.getEno() != null) {
		    	Experience existingExperience = ExperienceRepository.findById(experience1.getEno()).orElseThrow();
		        existingExperience.update(experience1); 
		        ExperienceRepository.save(existingExperience);
		    } else {
		      
		    }
		}
		
		for (Hope hope1 : hopeList) {
		    if (hope1.getHno() != null) {
		    	Hope existingHope = HopeReository.findById(hope1.getHno()).orElseThrow();
		        existingHope.update(hope1); 
		        HopeReository.save(existingHope);
		    } else {
		       
		    }
		}
		
		for (License license1 : licenseList) {
		    if (license1.getLno() != null) {
		    	License existingLicense = LicenseRepository.findById(license1.getLno()).orElseThrow();
		        existingLicense.update(license1); 
		        LicenseRepository.save(existingLicense);
		    } else {
		        
		    }
		}
		
		for (Oa oa1 : oaList) {
		    if (oa1.getOno() != null) {
		    	Oa existingOa = OaRepository.findById(oa1.getOno()).orElseThrow();
		        existingOa.update(oa1); 
		        OaRepository.save(existingOa);
		    } else {
		        
		    }
		}
		
		for (SelfInfo selfInfo1 : selfInfoList) {
		    if (selfInfo1.getSino() != null) {
		    	SelfInfo existingSelfInfo = SelfInfoRepository.findById(selfInfo1.getSino()).orElseThrow();
		        existingSelfInfo.update(selfInfo1); 
		        SelfInfoRepository.save(existingSelfInfo);
		    } else {
		        
		    }
		}

		ResumeRepository.save(resume);

		return "redirect:/user/user_resumeList";
	}
	
	@RequestMapping("/delete")
	public String delete() {
		
		
		
		return "redirect:/user/user_regList";
	}
}
