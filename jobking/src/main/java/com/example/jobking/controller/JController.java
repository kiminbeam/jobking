package com.example.jobking.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.jobking.entity.Company;
import com.example.jobking.entity.JobAd;
import com.example.jobking.entity.Resume;
import com.example.jobking.entity.User;
import com.example.jobking.repository.ICompanyRepository;
import com.example.jobking.repository.IJobAdRepository;
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
	private IJobAdRepository jobadRepo;
	@Autowired
	private ServletContext servletContext;
	
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
				user.setPhoto(file.getOriginalFilename());
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
//		System.out.println(jobAdList);
	}
	@RequestMapping("/user_recruit_job")
	public void userRecruitJob() {
	}
	@RequestMapping("/user_recruit_sector")
	public void userRecruitSector() {
	}

	@RequestMapping("/user_recruitDetail")
	public void userRecruitDetail(@RequestParam("jno") Long jno, Model model) {

//		 List<Company> companies = Arrays.asList(
//	                new Company("cid1", "logo1.png", "회사 A", "password1", "대표 A", "10001", "주소 A", "IT", 100, "http://companya.com", "대기업", "기술", "100억"),
//	                new Company("cid2", "logo2.png", "회사 B", "password2", "대표 B", "10002", "주소 B", "금융", 200, "http://companyb.com", "중견기업", "은행업", "200억"),
//	                new Company("cid3", "logo3.png", "회사 C", "password3", "대표 C", "10003", "주소 C", "헬스케어", 150, "http://companyc.com", "대기업", "제약", "150억"),
//	                new Company("cid4", "logo4.png", "회사 D", "password4", "대표 D", "10004", "주소 D", "교육", 120, "http://companyd.com", "소기업", "이러닝", "120억"),
//	                new Company("cid5", "logo5.png", "회사 E", "password5", "대표 E", "10005", "주소 E", "소매", 300, "http://companye.com", "대기업", "전자상거래", "300억"),
//	                new Company("cid6", "logo6.png", "회사 F", "password6", "대표 F", "10006", "주소 F", "건설", 80, "http://companyf.com", "중견기업", "부동산", "80억"),
//	                new Company("cid7", "logo7.png", "회사 G", "password7", "대표 G", "10007", "주소 G", "운송", 250, "http://companyg.com", "대기업", "물류", "250억"),
//	                new Company("cid8", "logo8.png", "회사 H", "password8", "대표 H", "10008", "주소 H", "에너지", 400, "http://companyh.com", "대기업", "석유 및 가스", "400억"),
//	                new Company("cid9", "logo9.png", "회사 I", "password9", "대표 I", "10009", "주소 I", "통신", 350, "http://companyi.com", "대기업", "통신", "350억"),
//	                new Company("cid10", "logo10.png", "회사 J", "password10", "대표 J", "10010", "주소 J", "자동차", 220, "http://companyj.com", "대기업", "제조업", "220억")
//	            );
//	            
//	            companyRepo.saveAll(companies);
//
//	            // Create job ads
//	            List<JobAd> jobAds = Arrays.asList(
//	                JobAd.builder().company(companies.get(0)).sectors("IT").wantedTitle("소프트웨어 엔지니어").position("개발자").jobCont("소프트웨어 애플리케이션을 개발 및 유지보수합니다.").receiptCloseDt(LocalDate.now().plusDays(30)).empTpNm("정규직").collectPsncnt(5).salTpNm("연봉").minEdubglcd("학사").maxEdubglcd("석사").mltsvcExcHope("N").needskill("Java").rcptMthd("온라인").regionCd("서울").WkdWkhCnt("40").retirepay("유").etcWelfare("건강 보험").attachFileUrl("jobdesc.pdf").attachFileUrl2("jobdesc2.pdf").srchKeywordNm("개발자").enterTpCd("정규직").salTpCd("연봉").empName("홍길동").empEmail("hong@company.com").empTel("010-1234-5678").build(),
//	                JobAd.builder().company(companies.get(1)).sectors("금융").wantedTitle("재무 분석가").position("분석가").jobCont("재무 데이터를 분석하고 보고서를 작성합니다.").receiptCloseDt(LocalDate.now().plusDays(25)).empTpNm("정규직").collectPsncnt(3).salTpNm("연봉").minEdubglcd("학사").maxEdubglcd("석사").mltsvcExcHope("N").needskill("엑셀").rcptMthd("온라인").regionCd("부산").WkdWkhCnt("40").retirepay("유").etcWelfare("건강 보험").attachFileUrl("jobdesc.pdf").attachFileUrl2("jobdesc2.pdf").srchKeywordNm("분석가").enterTpCd("정규직").salTpCd("연봉").empName("김철수").empEmail("kim@company.com").empTel("010-2345-6789").build(),
//	                JobAd.builder().company(companies.get(2)).sectors("헬스케어").wantedTitle("의료 연구원").position("연구원").jobCont("의료 연구 및 임상 시험을 진행합니다.").receiptCloseDt(LocalDate.now().plusDays(20)).empTpNm("정규직").collectPsncnt(2).salTpNm("연봉").minEdubglcd("박사").maxEdubglcd("박사").mltsvcExcHope("N").needskill("연구").rcptMthd("온라인").regionCd("인천").WkdWkhCnt("40").retirepay("유").etcWelfare("건강 보험").attachFileUrl("jobdesc.pdf").attachFileUrl2("jobdesc2.pdf").srchKeywordNm("연구원").enterTpCd("정규직").salTpCd("연봉").empName("이영희").empEmail("lee@company.com").empTel("010-3456-7890").build(),
//	                JobAd.builder().company(companies.get(3)).sectors("교육").wantedTitle("교사").position("강사").jobCont("학생들을 가르치고 교육 자료를 준비합니다.").receiptCloseDt(LocalDate.now().plusDays(15)).empTpNm("정규직").collectPsncnt(4).salTpNm("연봉").minEdubglcd("학사").maxEdubglcd("석사").mltsvcExcHope("N").needskill("교육").rcptMthd("온라인").regionCd("대구").WkdWkhCnt("40").retirepay("유").etcWelfare("건강 보험").attachFileUrl("jobdesc.pdf").attachFileUrl2("jobdesc2.pdf").srchKeywordNm("교사").enterTpCd("정규직").salTpCd("연봉").empName("박영수").empEmail("park@company.com").empTel("010-4567-8901").build(),
//	                JobAd.builder().company(companies.get(4)).sectors("소매").wantedTitle("매장 관리자").position("관리자").jobCont("매장 운영 및 판매를 관리합니다.").receiptCloseDt(LocalDate.now().plusDays(10)).empTpNm("정규직").collectPsncnt(1).salTpNm("연봉").minEdubglcd("학사").maxEdubglcd("학사").mltsvcExcHope("N").needskill("관리").rcptMthd("온라인").regionCd("광주").WkdWkhCnt("40").retirepay("유").etcWelfare("건강 보험").attachFileUrl("jobdesc.pdf").attachFileUrl2("jobdesc2.pdf").srchKeywordNm("관리자").enterTpCd("정규직").salTpCd("연봉").empName("최민수").empEmail("choi@company.com").empTel("010-5678-9012").build(),
//	                JobAd.builder().company(companies.get(5)).sectors("건설").wantedTitle("프로젝트 관리자").position("관리자").jobCont("건설 프로젝트를 관리하고 팀을 감독합니다.").receiptCloseDt(LocalDate.now().plusDays(5)).empTpNm("정규직").collectPsncnt(2).salTpNm("연봉").minEdubglcd("학사").maxEdubglcd("석사").mltsvcExcHope("N").needskill("프로젝트 관리").rcptMthd("온라인").regionCd("대전").WkdWkhCnt("40").retirepay("유").etcWelfare("건강 보험").attachFileUrl("jobdesc.pdf").attachFileUrl2("jobdesc2.pdf").srchKeywordNm("관리자").enterTpCd("정규직").salTpCd("연봉").empName("김지훈").empEmail("kimj@company.com").empTel("010-6789-0123").build(),
//	                JobAd.builder().company(companies.get(6)).sectors("운송").wantedTitle("물류 코디네이터").position("코디네이터").jobCont("물류 및 공급망 운영을 조정합니다.").receiptCloseDt(LocalDate.now().plusDays(25)).empTpNm("정규직").collectPsncnt(3).salTpNm("연봉").minEdubglcd("학사").maxEdubglcd("학사").mltsvcExcHope("N").needskill("물류").rcptMthd("온라인").regionCd("울산").WkdWkhCnt("40").retirepay("유").etcWelfare("건강 보험").attachFileUrl("jobdesc.pdf").attachFileUrl2("jobdesc2.pdf").srchKeywordNm("코디네이터").enterTpCd("정규직").salTpCd("연봉").empName("이민호").empEmail("lee@company.com").empTel("010-7890-1234").build(),
//	                JobAd.builder().company(companies.get(7)).sectors("에너지").wantedTitle("에너지 분석가").position("분석가").jobCont("에너지 소비 및 효율성을 분석합니다.").receiptCloseDt(LocalDate.now().plusDays(20)).empTpNm("정규직").collectPsncnt(2).salTpNm("연봉").minEdubglcd("학사").maxEdubglcd("석사").mltsvcExcHope("N").needskill("분석").rcptMthd("온라인").regionCd("세종").WkdWkhCnt("40").retirepay("유").etcWelfare("건강 보험").attachFileUrl("jobdesc.pdf").attachFileUrl2("jobdesc2.pdf").srchKeywordNm("분석가").enterTpCd("정규직").salTpCd("연봉").empName("박수진").empEmail("park@company.com").empTel("010-8901-2345").build(),
//	                JobAd.builder().company(companies.get(8)).sectors("통신").wantedTitle("네트워크 엔지니어").position("엔지니어").jobCont("네트워크 시스템을 설계하고 유지보수합니다.").receiptCloseDt(LocalDate.now().plusDays(15)).empTpNm("정규직").collectPsncnt(4).salTpNm("연봉").minEdubglcd("학사").maxEdubglcd("석사").mltsvcExcHope("N").needskill("네트워킹").rcptMthd("온라인").regionCd("경기").WkdWkhCnt("40").retirepay("유").etcWelfare("건강 보험").attachFileUrl("jobdesc.pdf").attachFileUrl2("jobdesc2.pdf").srchKeywordNm("엔지니어").enterTpCd("정규직").salTpCd("연봉").empName("김서연").empEmail("kim@company.com").empTel("010-9012-3456").build(),
//	                JobAd.builder().company(companies.get(9)).sectors("자동차").wantedTitle("기계 엔지니어").position("엔지니어").jobCont("자동차 부품을 설계하고 개발합니다.").receiptCloseDt(LocalDate.now().plusDays(10)).empTpNm("정규직").collectPsncnt(3).salTpNm("연봉").minEdubglcd("학사").maxEdubglcd("석사").mltsvcExcHope("N").needskill("기계공학").rcptMthd("온라인").regionCd("충남").WkdWkhCnt("40").retirepay("유").etcWelfare("건강 보험").attachFileUrl("jobdesc.pdf").attachFileUrl2("jobdesc2.pdf").srchKeywordNm("엔지니어").enterTpCd("정규직").salTpCd("연봉").empName("이정현").empEmail("lee@company.com").empTel("010-0123-4567").build()
//	            );
//
//	            jobadRepo.saveAll(jobAds);
		
		JobAd jobad = jobadRepo.findById(jno).get();
		model.addAttribute("jobad", jobad);
		
	}
}
