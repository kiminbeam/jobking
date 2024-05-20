package com.example.jobking.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	private final Path rootLocation = Paths.get("/upload");
	
	
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
	public String userEdit(@RequestParam("photo") MultipartFile file, User user) {
		 try {
	            // 만약 업로드할 폴더 없으면 만들기
	            if (!Files.exists(rootLocation)) {
	                Files.createDirectories(rootLocation);
	            }

	            if (file != null && !file.isEmpty()) {
	                // 파일업로드
	                String originalFilename = file.getOriginalFilename();
	                String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
	                String filename = UUID.randomUUID().toString() + extension;
	                Path destinationFile = this.rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();

	                // 파일이 이미 존재하면 덮어쓰기 또는 다른 처리를 해야 할 수 있음
	                Files.copy(file.getInputStream(), destinationFile);

	                String filePath = destinationFile.toString();

	                // User 엔티티에 파일 정보 설정
	                user.setFileName(filename);
	                user.setFilePath(filePath);
	                user.setFileSize(file.getSize());

	                // User 엔티티 저장
	                userRepo.save(user);
	            }
	        } catch (IOException e) {
	            throw new RuntimeException("Could not create upload directory or save file!", e);
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
