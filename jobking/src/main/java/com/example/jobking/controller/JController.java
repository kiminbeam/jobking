package com.example.jobking.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.jobking.entity.User;
import com.example.jobking.repository.IUserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class JController {

	
	@Autowired
	private IUserRepository userRepo;
	
	@RequestMapping("/index")
	public String root(Model model) {
		model.addAttribute("name", "jia");
		model.addAttribute("name111", "yaaay");
		return "index";
	}
	
	@RequestMapping("/login_form")
	public void loginForm() {
		
	}
	@RequestMapping("/user_login")
	public String userLogin(HttpServletRequest request, Model model) {
		String uid = request.getParameter("uid");
		String upw = request.getParameter("upw");
	
		//db에서 user 로그인 정보 확인
		System.out.println(userRepo.findById(uid));
		System.out.println(userRepo.findById(uid).isEmpty());
		System.out.println(userRepo.findById(uid).isPresent());
		Optional<User> user = userRepo.findById(uid);
		user.ifPresent(u->{
			if(u.getUpw().equals(upw)) {
				
				request.getSession().setAttribute("id", uid);
				request.getSession().setAttribute("name", u.getUname());
				model.addAttribute("result", "true");
			}
		});
		return "login_form";
	}
	@RequestMapping("/company_login")
	public String companyLogin(HttpServletRequest request, Model model) {
		String cid = request.getParameter("cid");
		String cpw = request.getParameter("cpw");
		System.out.println(cid);
		//db에서 user 로그인 정보 확인
	    //맞으면 result 보내주기
		return "redirect:/";
	}
	@RequestMapping("/user_myPage")
	public void userMyPage(Model model) {
		
//		userRepo.save(new User("aaa", "홍길동","1234", new Date(), "M", "aaa1234@gmail.com","010-1111-1111", "서울","dog"));
		
	}
}
