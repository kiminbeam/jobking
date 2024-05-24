package com.example.jobking.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.jobking.entity.ApplyList;
import com.example.jobking.entity.Company;
import com.example.jobking.entity.OfferList;
import com.example.jobking.entity.User;
import com.example.jobking.entity.UserReview;
import com.example.jobking.repository.ICompanyRepository;
import com.example.jobking.repository.IOfferListRepository;
import com.example.jobking.repository.IUserApplyListRepository;
import com.example.jobking.repository.IUserRepository;
import com.example.jobking.repository.IUserReviewRepository;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/user")
@Controller
public class YController {

	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	ICompanyRepository companyRepository;
	
	@Autowired
	IUserApplyListRepository applyListRepo;
	
	@Autowired
	IOfferListRepository offerListRepo;
	
	@Autowired
	IUserReviewRepository reviewRepo;
	
	@RequestMapping("/user_regForm")
	public String regForm() {
		return "/user/user_regForm";
	}
	
	// User 아이디 중복체크
	@RequestMapping("/uidCheck")
	public @ResponseBody String uidCheck(@RequestParam("uid") String uid) {
		Optional<User> result = userRepository.findById(uid);
		String msg = "";
		System.out.println(result);
		if(result.isPresent()) {
			msg = "중복된 아이디 입니다.";
		}else {
			msg = "사용가능한 아이디 입니다.";
		}
		return msg;
	}
	
	
	// Company 아이디 중복체크
	@RequestMapping("/cidCheck")
	public @ResponseBody String cidCheck(@RequestParam("cid") String cid) {
		Optional<Company> result = companyRepository.findById(cid);
		String msg = "";
		System.out.println(result);
		if(result.isPresent()) {
			msg = "중복된 아이디 입니다.";
		}else {
			msg = "사용가능한 아이디 입니다.";
		}
		return msg;
	}
	
	// User 회원가입 기능
	@RequestMapping("/user_regist")
	public String userReg(User user) {
		userRepository.save(user);
		return "/user/login_form";
	}
	
	// Company 회원가입 기능
	@RequestMapping("/com_regist")
	public String comReg(Company company) {
		companyRepository.save(company);
		return "/user/login_form";
	}
	
	// 입사지원관리	
	@RequestMapping("/user_apply_list")
	public void userApplyList(HttpServletRequest request, Model model) {
		String uid = (String)request.getSession().getAttribute("id");
		List<ApplyList> applyList = applyListRepo.findByUid(uid);
		model.addAttribute("apply", applyList);
		System.out.println(applyList);
	}
	
	@RequestMapping("/deleteUserApplyList")
	public String deleteUserApplyList(@RequestParam("ano") String ano) {
		applyListRepo.deleteById(Long.parseLong(ano));
		return "redirect:/user/user_apply_list";
	}
	
	// 평점관리 폼
	@RequestMapping("/user_review_form")
	public void userReviewForm(HttpServletRequest request, Model model) {
		String uid = (String)request.getSession().getAttribute("id");
		
		List<ApplyList> applyList = applyListRepo.findByUid(uid);
		List<OfferList> offerList= offerListRepo.findByUid(uid);
		model.addAttribute("apply", applyList);
		model.addAttribute("offer", offerList);
	}
	
	@RequestMapping("/user_review_regist")
	public String user_review_regist(UserReview userReivew, HttpServletRequest request){
		
		// uid 저장
		String uid = (String)request.getSession().getAttribute("id");
		User user = userRepository.findById(uid).get();
		userReivew.setUser(user);
		
		// cid 저장
		String cid = request.getParameter("cid");
		Company company = companyRepository.findById(cid).get();
		userReivew.setCompany(company);
		
		System.out.println(userReivew);
		//reviewRepo.save(userReivew);
		
		return "redirect:/user/user_review_list";
	}
	
}
