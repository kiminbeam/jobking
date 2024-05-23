package com.example.jobking.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

	@PostMapping("/user_resume_form")
	public String createResume(HttpServletRequest request) {
		String uid = (String) request.getSession().getAttribute("id");
		Optional<User> user = UserRepository.findById(uid);
		if (user.isPresent()) {
			Resume resume = new Resume();
			resume.setUser(user.get());
			ResumeRepository.save(resume);
		}
		return "redirect:user_resumeList";
	}

	@PostMapping("/user_resume")
	public String resume(HttpServletRequest request, Resume resume, School school, Career career, Hope hope,
			SelfInfo selfInfo, Oa oa, License license, Experience experience) {

		String uid = (String) request.getSession().getAttribute("id");
		String rno = request.getParameter("rno");
		System.out.println(rno);
		User user = UserRepository.findById(uid).get();

		// 해당 아이디의 이력서가 이미 존재하는지 확인
		List<Resume> resumeExists = ResumeRepository.findByUid(uid);
		System.out.println(resumeExists);
		System.out.println(resumeExists == null);
		System.out.println(resumeExists.size() == 1);
		// 기존에 저장된 이력서가 없으면 def 값을 "1"로 설정
//		if (resumeExists.size() == 1) {
//			resume.setDef("1");
//		}
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

	
	
}
