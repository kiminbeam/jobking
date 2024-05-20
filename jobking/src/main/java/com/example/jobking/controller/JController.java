package com.example.jobking.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.jobking.entity.Company;
import com.example.jobking.entity.InterestCop;
import com.example.jobking.entity.JobAd;
import com.example.jobking.entity.JobScrap;
import com.example.jobking.entity.Resume;
import com.example.jobking.entity.User;
import com.example.jobking.repository.ICompanyRepository;
import com.example.jobking.repository.IInterestCopRepository;
import com.example.jobking.repository.IJobAdRepository;
import com.example.jobking.repository.IJobScrapRepository;
import com.example.jobking.repository.IResumeRepository;
import com.example.jobking.repository.IUserRepository;

import jakarta.servlet.ServletContext;
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
	@Autowired
	private IInterestCopRepository interestRepo;
	@Autowired
	private IJobAdRepository jobadRepo;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private IJobScrapRepository jobscrapRepo;
	
	
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
	public String userEdit(@RequestParam("photo") MultipartFile file, HttpServletRequest request) {
		User oriUser = userRepo.findById(request.getParameter("uid")).get();
		User user = new User();
		user = oriUser;
		user.setUpw(request.getParameter("upw"));
		user.setUname(request.getParameter("uname"));
		user.setUaddr(request.getParameter("uaddr"));
		user.setTel(request.getParameter("tel"));
		user.setEmail(request.getParameter("email"));
		System.out.println(file.getOriginalFilename());
		if(!file.isEmpty()) {
			 String uploadDir = servletContext.getRealPath("/images/");
		     File destPath = new File(uploadDir + File.separator + file.getOriginalFilename());
			try {
				
				file.transferTo(destPath);
//				user.setPhoto(file.getOriginalFilename());
				userRepo.save(user);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "redirect:/user/user_myPage";
	}
	@RequestMapping("/user_logout")
	public String userLogout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/user/index";
	}

	
	@RequestMapping("/user_recruit_region")
	public void userRecruitRegion(Model model) {
		List<JobAd> jobAdList = jobadRepo.findAll();
		model.addAttribute("jobadList", jobAdList);
	}
	@RequestMapping("/user_recruit_job")
	public void userRecruitJob() {
	}
	@RequestMapping("/user_recruit_sector")
	public void userRecruitSector() {
	}

	@RequestMapping("/user_recruitDetail")
	public void userRecruitDetail(@RequestParam("jno") Long jno, Model model) {
		JobAd jobad = jobadRepo.findById(jno).get();
		model.addAttribute("jobad", jobad);
	}
	@RequestMapping("/user_subNscrap_list")
	public void userSubNscrapList(Model model) {
		List<InterestCop> interestCopList = interestRepo.findAll();
		List<JobScrap> jobscrapList = jobscrapRepo.findAll();
		
		model.addAttribute("interestCopList", interestCopList);
		model.addAttribute("jobscrapList", jobscrapList);
		System.out.println(jobscrapList);
	}
	@RequestMapping("/delete_interestCop")
	public String deleteInterestCop(@RequestParam("interno") String interno) {
		interestRepo.deleteById(Long.parseLong(interno)); 
		return"redirect:/user/user_subNscrap_list";
	}
	@RequestMapping("/delete_jobScrap")
	public String deletJobScrap(@RequestParam("jno") String jno) {
		jobscrapRepo.deleteById(Long.parseLong(jno));
		return"redirect:/user/user_subNscrap_list";
	}
}
