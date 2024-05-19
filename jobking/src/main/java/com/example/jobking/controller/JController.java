package com.example.jobking.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.jobking.entity.Company;
import com.example.jobking.entity.Resume;
import com.example.jobking.entity.User;
import com.example.jobking.repository.ICompanyRepository;
import com.example.jobking.repository.IResumeRepository;
import com.example.jobking.repository.IUserRepository;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/user")
@Controller
public class JController {

	
	@Autowired
	private IUserRepository userRepo;
	@Autowired
	private IResumeRepository resumeRepo;
	
	@Autowired
	private ICompanyRepository companyRepo;
	
	@RequestMapping("/index")
	public String root() {
		companyRepo.save(new Company("ccc","", "네이버", "12345", "12345", "james", "11111", "서울", 500, "", "11", "11", "11"));
//		userRepo.save(new User("aab", "james","1234", LocalDate.now(), "M", "aaa1234@gmail.com","010-1111-1111", "서울","dog"));
		return "/user/index";
	}
	
	@RequestMapping("/login_form")
	public void loginForm() {
		
	}
	@RequestMapping("/user_login")
	public String userLogin(HttpServletRequest request, Model model) {
		String uid = request.getParameter("uid");
		String upw = request.getParameter("upw");
		boolean result = false;
		
		//db에서 user 로그인 정보 확인
		Optional<User> user = userRepo.findById(uid);
		if(user.isPresent()) {
			if(user.get().getUpw().equals(upw)) {
				request.getSession().setAttribute("id", uid);
				request.getSession().setAttribute("name", user.get().getUname());
				result = true;
				model.addAttribute("result", true);
			}
		}else {
			model.addAttribute("result", false);
		}
		return result ? "/user/index" : "/user/login_form";
	}
	@RequestMapping("/company_login")
	public String companyLogin(HttpServletRequest request, Model model) {
		String cid = request.getParameter("cid");
		String cpw = request.getParameter("cpw");
		boolean result = false;
		//db에서 user 로그인 정보 확인
		Optional<Company> company = companyRepo.findById(cid);
		if(company.isPresent()) {
			if(company.get().getCpw().equals(cpw)) {
				request.getSession().setAttribute("id", cid);
				request.getSession().setAttribute("name", company.get().getCname());
				result = true;
				model.addAttribute("result", true);
			}
		}else {
			model.addAttribute("result", false);
		}
		return result ? "/user/index" : "/user/login_form";
	}
	@RequestMapping("/user_myPage")
	public void userMyPage(HttpServletRequest request, Model model) {
		String uid = (String) request.getSession().getAttribute("id");
		//회원 개인정보 얻기
		Optional<User> user = userRepo.findById(uid);
		user.ifPresent(u->{
			model.addAttribute("user",u );
		});
		//회원 이력서 리스트 가져오기
		if(resumeRepo.findByUid(uid).size() != 0) {
			List<Resume> li = resumeRepo.findByUid(uid);
			System.out.println(li);
			model.addAttribute("resumeList", resumeRepo.findByUid(uid));
		}else {
			model.addAttribute("result", "false");
		}
	}
	@RequestMapping("/user_information")
	public void userInformation(HttpServletRequest request, Model model) {
		String uid = (String) request.getSession().getAttribute("id");
		//회원 개인정보 얻기
		Optional<User> user = userRepo.findById(uid);
		user.ifPresent(u->{
			model.addAttribute("user",u );
		});
	}
	@RequestMapping("/user_edit")
	public String userEdit(User user) {
		userRepo.save(user);
		
		return "redirect:/user/user_myPage";
	}
	@RequestMapping("/user_logout")
	public String userLogout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/user/index";
	}

	
	@RequestMapping("/user_recruit_region")
	public void userRecruitRegion() {
	}
	@RequestMapping("/user_recruit_job")
	public void userRecruitJob() {
	}
	@RequestMapping("/user_recruit_sector")
	public void userRecruitSector() {
	}

}
