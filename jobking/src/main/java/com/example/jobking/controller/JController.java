package com.example.jobking.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.checkerframework.checker.units.qual.s;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.jobking.dto.JobAdWithScrapAndLike;
import com.example.jobking.entity.AbgLoginTime;
import com.example.jobking.entity.ApplyList;
import com.example.jobking.entity.Career;
import com.example.jobking.entity.Company;
import com.example.jobking.entity.CompanyBoard;
import com.example.jobking.entity.CompanyReply;
import com.example.jobking.entity.CompanyReview;
import com.example.jobking.entity.Experience;
import com.example.jobking.entity.Hope;
import com.example.jobking.entity.InterestCop;
import com.example.jobking.entity.JobAd;
import com.example.jobking.entity.JobScrap;
import com.example.jobking.entity.License;
import com.example.jobking.entity.Oa;
import com.example.jobking.entity.OfferList;
import com.example.jobking.entity.Resume;
import com.example.jobking.entity.School;
import com.example.jobking.entity.SelfInfo;
import com.example.jobking.entity.User;
import com.example.jobking.entity.UserBoard;
import com.example.jobking.entity.UserReply;
import com.example.jobking.entity.UserReview;
import com.example.jobking.repository.IAbgLoginTimeRepository;
import com.example.jobking.repository.IApplyListRepository;
import com.example.jobking.repository.ICareerRepository;
import com.example.jobking.repository.ICompanyRepository;
import com.example.jobking.repository.ICompanyReviewRepository;
import com.example.jobking.repository.IExperienceRepository;
import com.example.jobking.repository.IHopeRepository;
import com.example.jobking.repository.IInterestCopRepository;
import com.example.jobking.repository.IJobAdRepository;
import com.example.jobking.repository.IJobScrapRepository;
import com.example.jobking.repository.ILicenseRepository;
import com.example.jobking.repository.IOaRepository;
import com.example.jobking.repository.IOfferListRepository;
import com.example.jobking.repository.IResumeRepository;
import com.example.jobking.repository.ISchoolRepository;
import com.example.jobking.repository.ISelfInfo;
import com.example.jobking.repository.IUserBoardRepository;
import com.example.jobking.repository.IUserReplyRepository;
import com.example.jobking.repository.IUserRepository;
import com.example.jobking.repository.IUserReviewRepository;

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
	@Autowired
	private IOfferListRepository offerListRepo;
	@Autowired
	private IUserReviewRepository userReviewRepo;
	@Autowired
	private ICompanyReviewRepository companyReviewRepo;
	@Autowired
	private IUserBoardRepository userBoardRepo;
	@Autowired
	private IUserReplyRepository userReplyRepo;
	@Autowired
	private IHopeRepository hopeRepo;
	@Autowired
	private IAbgLoginTimeRepository abgLoginRepo;
	@Autowired
	ISelfInfo selfinfoRepo;
	@Autowired
	ICareerRepository careerRepo;
	@Autowired
	IOaRepository oaRepo;
	@Autowired
	ISchoolRepository schoolRepo;
	@Autowired
	ILicenseRepository licenseRepo;
	@Autowired
	IExperienceRepository experienceRepo;
	@Autowired
	IApplyListRepository applyRepo;
	private final Path rootLocation = Paths.get("/upload");
	
	
	@RequestMapping("/index")
	public String root() {
//		companyRepo.save(new Company("ccc","", "네이버", "12345", "12345", "james", "11111", "서울", 500, "", "11", "11", "11"));
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
				//먼저 유저의 지난마지막 로그인 시간 구하기
//				Date lastLogin = abgLoginRepo.findLatestAbgLoginTime(uid).get().getEndTime();
				////로그인시 avg_loginTime에 로그인 startTime넣어주기
				AbgLoginTime abgLoginTime = new AbgLoginTime();
				abgLoginTime.setUser(userRepo.findById(uid).get());
				abgLoginTime.setStartTime(new Date());
				abgLoginRepo.save(abgLoginTime);
			}
		}else {
			model.addAttribute("result", false);
		}
		return result ? "/user/user_mainPage" : "/user/login_form";
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
				////로그인시 avg_loginTime에 로그인 startTime넣어주기
				AbgLoginTime abgLoginTime = new AbgLoginTime();
				abgLoginTime.setCompany(companyRepo.findById(cid).get());
				abgLoginTime.setStartTime(new Date());
				abgLoginRepo.save(abgLoginTime);
				System.out.println("로그인 컨트롤러 실행~~~~~~~~~~~~~");
				
			}
			
		}else {
			model.addAttribute("result", false);
		}
		return result ? "/company/cpmain" : "/user/login_form";
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
		//개인평균평점 
		Double avgReview = userReviewRepo.findAverageByUid(uid);
		model.addAttribute("avgReview", String.format("%.2f", avgReview));
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
		String uid = (String) request.getSession().getAttribute("id");
	   //로그아웃시간 abgLoginTime에 넣어주기
		AbgLoginTime alt = abgLoginRepo.findLatestAbgLoginTime(uid).get();
		alt.setEndTime(new Date());
		abgLoginRepo.save(alt);
		request.getSession().invalidate();
		return "/user/index";
	}
	
	@RequestMapping("/user_recruit_region")
	public void userRecruitRegion(HttpServletRequest request, Model model) {
		List<JobAd> jobAdList = jobadRepo.findAll();
		List<JobAdWithScrapAndLike> newList = new ArrayList<>();
		String uid = (String) request.getSession().getAttribute("id");
		//스크랩되있는 jno 모음
		List<Long> scrappedJno = jobscrapRepo.findScrapedJobAdByUid(uid);
		//구독한 기업 cid모음
		//전체 리스트 뿌려주기
		for(JobAd ja : jobAdList) {
			JobAdWithScrapAndLike jobDto = new JobAdWithScrapAndLike();
			jobDto.setJobad(ja);
			for(Long ln : scrappedJno) {
				if(ja.getJno() == ln) {
					jobDto.setScrapped(true);
				}
			}
			System.out.println(jobDto.getClass());
			newList.add(jobDto);
		}
		model.addAttribute("newList", newList);
	}
	@RequestMapping("/user_recruit_regionSearch")
	public @ResponseBody List<JobAd> userRecruitRegionSearch(HttpServletRequest request, @RequestParam(required = false, name = "region1Name") String region1Name,
	        @RequestParam(required = false, name = "region2Name") String region2Name,
	        @RequestParam(required = false, name = "sector1Name") String sector1Name,
	        @RequestParam(required = false, name = "sector2Name") String sector2Name,
	        @RequestParam(required = false, name = "position1Name") String position1Name,
	        @RequestParam(required = false, name = "position2Name") String position2Name) {
		String uid = (String) request.getSession().getAttribute("id");
		System.out.println(uid);
		 List<JobAd> list = jobadRepo.findJobad(region1Name, region2Name, sector1Name,sector2Name,position1Name,position2Name);

		return list;
	}

	@RequestMapping("/user_recruit_job")
	public void userRecruitJob(HttpServletRequest request, Model model) {
		List<JobAd> jobAdList = jobadRepo.findAll();
		List<JobAdWithScrapAndLike> newList = new ArrayList<>();
		String uid = (String) request.getSession().getAttribute("id");
		//스크랩되있는 jno 모음
		List<Long> scrappedJno = jobscrapRepo.findScrapedJobAdByUid(uid);
		//구독한 기업 cid모음
		//전체 리스트 뿌려주기
		for(JobAd ja : jobAdList) {
			JobAdWithScrapAndLike jobDto = new JobAdWithScrapAndLike();
			jobDto.setJobad(ja);
			for(Long ln : scrappedJno) {
				if(ja.getJno() == ln) {
					jobDto.setScrapped(true);
				}
			}
			System.out.println(jobDto.getClass());
			newList.add(jobDto);
		}
		model.addAttribute("newList", newList);
	}
	@RequestMapping("/user_recruit_sector")
	public void userRecruitSector(HttpServletRequest request, Model model) {
		List<JobAd> jobAdList = jobadRepo.findAll();
		List<JobAdWithScrapAndLike> newList = new ArrayList<>();
		String uid = (String) request.getSession().getAttribute("id");
		//스크랩되있는 jno 모음
		List<Long> scrappedJno = jobscrapRepo.findScrapedJobAdByUid(uid);
		//구독한 기업 cid모음
		//전체 리스트 뿌려주기
		for(JobAd ja : jobAdList) {
			JobAdWithScrapAndLike jobDto = new JobAdWithScrapAndLike();
			jobDto.setJobad(ja);
			for(Long ln : scrappedJno) {
				if(ja.getJno() == ln) {
					jobDto.setScrapped(true);
				}
			}
			System.out.println(jobDto.getClass());
			newList.add(jobDto);
		}
		model.addAttribute("newList", newList);
	}

	@RequestMapping("/user_recruitDetail")
	public void userRecruitDetail(HttpServletRequest request, @RequestParam("jno") Long jno, Model model) {
	String uid = (String) request.getSession().getAttribute("id");
		
		//해당 채용공고 정보 보내주기
		JobAd jobad = jobadRepo.findById(jno).get();
		model.addAttribute("jobad", jobad);
		//해당 채용공고 직무설명 리스트 보내주기
		model.addAttribute("jobCont", jobad.getJobContList());
		//해당 채용공고 스킬리스트 보내주기
		model.addAttribute("jobSkill", jobad.getNeedskillList());
		//해당 채용공고 키워드리스트 보내주기
		model.addAttribute("jobKeyword", jobad.getSrchKeywordNmList());
		
		//해당기업 별점 정보보내주기
		Double avgReview = companyReviewRepo.findAverageByCid(jobad.getCompany().getCid());
		model.addAttribute("avgReview", String.format("%.2f", avgReview));
		//스크랩 여부 정보 보내주기
		Optional<JobScrap> checkS = jobscrapRepo.findByUidNJno(uid,jno);
		if(checkS.isEmpty()) {
			model.addAttribute("scrap", false);
		}else {
			model.addAttribute("scrap", true);
		}
		//해당기업 찜 여부 정보 보내주기
		Optional<InterestCop> checkI = interestRepo.findByUidNCid(uid,jobad.getCompany().getCid());
		if(checkI.isEmpty()) {
			model.addAttribute("interest", false);
		}else {
			model.addAttribute("interest", true);
		}
		//해당공고에 지원한 사람 수 구하기(총사람수, 남자지원자수, 여자지원자수)
		
	
		
		int count = (applyRepo.findFemaleAppli(jno)+applyRepo.findMaleAppli(jno));
	
		model.addAttribute("female", applyRepo.findFemaleAppli(jno));
		model.addAttribute("male", applyRepo.findMaleAppli(jno));
		model.addAttribute("count", count);

	}
	@RequestMapping("/scrapJobad")
	public @ResponseBody String scrapJobad(HttpServletRequest request, @RequestParam("jno") String jno, Model model) {
		String uid = (String) request.getSession().getAttribute("id");
		Optional<JobScrap> check = jobscrapRepo.findByUidNJno(uid, Long.parseLong(jno));
		//만약 이미 등록된 공고가 있다면 삭제하기
		if(!check.isEmpty()) {
			jobscrapRepo.delete(check.get());
			return "deleted";
		}else {
			// 아니라면 더해주기
			JobScrap jobScrap = new JobScrap(0L, userRepo.findById(uid).get(), jobadRepo.findById(Long.parseLong(jno)).get());
			jobscrapRepo.save(jobScrap);
			return "added";
		}
	}
	@RequestMapping("/likeTheCom")
	public @ResponseBody String likeTheCom(HttpServletRequest request, @RequestParam("cid") String cid) {
		String uid = (String) request.getSession().getAttribute("id");
		Optional<InterestCop> check = interestRepo.findByUidNCid(uid,cid);
		//만약 이미 등록된 공고가 있다면 삭제하기
		if(!check.isEmpty()) {
			interestRepo.delete(check.get());
			return "deleted";
		}else {
			// 아니라면 더해주기
			InterestCop interestCop = new InterestCop(0L, userRepo.findById(uid).get(), companyRepo.findById(cid).get());
			interestRepo.save(interestCop);
			return "added";
		}
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
	
	
	@RequestMapping("/user_offer_list")
	public void userOfferList(HttpServletRequest request, Model model) {
		String uid = (String) request.getSession().getAttribute("id");
		System.out.println(uid);
		List<OfferList> offerList = offerListRepo.findAllByUid(uid);
		model.addAttribute("offerList", offerList);
		System.out.println(offerList);
	}
	
	
	@RequestMapping("/delete_offerList")
	public String deletOfferList(@RequestParam("ono") String ono) {
		offerListRepo.deleteById(Long.parseLong(ono));
		return"redirect:/user/user_offer_list";
	}
	@RequestMapping("/user_offer_detail")
	public void userOfferDetail(@RequestParam("ono") String ono, Model model) {
		OfferList offer = offerListRepo.findById(Long.parseLong(ono)).get();
		System.out.println(offer); 
		model.addAttribute("offer", offer);
	}
	@RequestMapping("/delete_offer_detail")
	public String deletOfferDetail(@RequestParam("ono") String ono) {
		offerListRepo.deleteById(Long.parseLong(ono));
		return"redirect:/user/user_offer_list";
	}
	@RequestMapping("/answerToOffer")
	public @ResponseBody String answerToOffer(@RequestParam("answer") String answer, @RequestParam("ono") String ono) {
		OfferList offer = offerListRepo.findById(Long.parseLong(ono)).get();
		offer.setAccept(answer);
		offerListRepo.save(offer);
		return "done";
	}
	@RequestMapping("/user_review_list")
	public void userReviewList(Model model) {
		List<UserReview> userReviewList = userReviewRepo.findAll();
		List<CompanyReview> companyReviewList = companyReviewRepo.findAll();
		model.addAttribute("companyReviewList",companyReviewList);
		model.addAttribute("userReviewList",userReviewList);
		
		System.out.println(userReviewList);
		System.out.println(companyReviewList);
	}
	@RequestMapping("/user_resumePick")
	public void userResumePick(HttpServletRequest request, Model model) {
		//해당 아이디로 등록된 이력서 몇개인지 받아오기
		String uid = (String) request.getSession().getAttribute("id");
		User user = userRepo.findById(uid).get();
		List<Resume> resumeList = resumeRepo.findByUid(uid);
		model.addAttribute("jno", request.getParameter("jno"));
		model.addAttribute("resumeList", resumeList);
	}
	@RequestMapping("/user_myBoard_list")
	public void userMyBoardList(HttpServletRequest request, Model model) {
		String uid = (String) request.getSession().getAttribute("id");
		List<UserBoard> userBoardList = userBoardRepo.findByUid(uid);
		List<UserReply> userReplyList = userReplyRepo.findByUid(uid);
		
		model.addAttribute("userBoardList", userBoardList);
		model.addAttribute("userReplyList", userReplyList);
		System.out.println(userBoardList);
		System.out.println(userReplyList);
	}
	@RequestMapping("/delete_board")
	public void deleteBoard(@RequestParam("ubno") Long ubno, HttpServletRequest request, Model model) {
		//해당 글에 달려있는 모든 댓글 먼저 다 지우기
		userReplyRepo.deleteAllByUbno(ubno);
		//선택한 글 지우기
		userBoardRepo.delete(userBoardRepo.findById(ubno).get());
	}
	@RequestMapping("/delete_reply")
	public void deleteReply(@RequestParam("replyno") Long replyno, Model model) {
		userReplyRepo.delete(userReplyRepo.findById(replyno).get());
	}
	@RequestMapping("/user_communityList")
	public void userCommunityList(Model model) {
		List<UserBoard> allList = userBoardRepo.findAll();
		List<UserBoard> t1List = userBoardRepo.findAllByType("1");
		List<UserBoard> t2List = userBoardRepo.findAllByType("2");
		List<UserBoard> t3List = userBoardRepo.findAllByType("3");
		UserBoard latestAlertBoard = userBoardRepo.findLatestBoardByType("3");
		model.addAttribute("allList",allList);
		model.addAttribute("t1List",t1List);
		model.addAttribute("t2List",t2List);
		model.addAttribute("t3List",t3List);
		model.addAttribute("latestAlertBoard",latestAlertBoard);
	}
	@RequestMapping("/user_community_detail")
	public void userCommunityDetail(@RequestParam("ubno") Long ubno, Model model) {
		UserBoard userBoard = userBoardRepo.findById(ubno).get();
		//가장 최신 공지사항 가져오기
		UserBoard latestAlertBoard = userBoardRepo.findLatestBoardByType("3");
		model.addAttribute("latestAlertBoard",latestAlertBoard);
		//해당글에 대한 정보가져오기
		model.addAttribute("userBoard", userBoard);
		//해당글에 대한 댓글 정보 가져오기
		List<UserReply> userReplyList = userReplyRepo.findAllByUbno(ubno);
		model.addAttribute("userReplyList", userReplyList);
	}
	
	//댓글작성
	@RequestMapping("/userReply_insert")
	public String insertUserReply(@RequestParam("ubno") Long ubno, @RequestParam("uid") String uid, @RequestParam("content") String content ,UserReply userReply,HttpServletRequest request) {
		Optional<UserBoard> list= userBoardRepo.findById(ubno);
		UserBoard board = list.get();
		
		Optional<User> user = userRepo.findById(uid);
		User u = user.get();
		
		userReply.setUserBoard(board);
		userReply.setUser(u);
		userReply.setContent(content);
		
		userReplyRepo.save(userReply);
		return "redirect:/user/user_community_detail?ubno=" + ubno;
	}
	
	
	
	@RequestMapping("/user_communityForm_insert")//등록 화면
	public void userCommunityFormInsert(UserBoard userBoard, Model model) {
		UserBoard latestAlertBoard = userBoardRepo.findLatestBoardByType("3");
		model.addAttribute("latestAlertBoard",latestAlertBoard);
	}
	@RequestMapping("/user_board_regist")//등록기능
	public void userBoardRegist(HttpServletRequest request, UserBoard userBoard) {
		String uid = (String) request.getSession().getAttribute("id");
		userBoard.setUser(userRepo.findById(uid).get());
		userBoardRepo.save(userBoard);
	}
	@RequestMapping("/user_communityForm_edit")//수정 화면
	public void userCommunityFormEdit(@RequestParam("ubno") Long ubno, Model model) {
		UserBoard latestAlertBoard = userBoardRepo.findLatestBoardByType("3");
		model.addAttribute("latestAlertBoard",latestAlertBoard);
		
		UserBoard userBoard = userBoardRepo.findById(ubno).get();
		model.addAttribute(userBoard);
	}


	@RequestMapping("/user_communityForm_update")//수정기능
	public String userCommunityFormUpdate(HttpServletRequest request, UserBoard userBoard, Model model) {
		String ubno = request.getParameter("ubno");
		UserBoard ub = userBoardRepo.findById(Long.parseLong(ubno)).get();
		ub.setTitle(userBoard.getTitle());
		ub.setContent(userBoard.getContent());
		ub.setType(userBoard.getType());
		System.out.println(ub);
		userBoardRepo.save(ub);
		return "redirect:/user/user_communityList";
	}
	
	@RequestMapping("/user_communityForm_delete")
	public String userCommunityFormDelete(@RequestParam("ubno") Long ubno) {
		userBoardRepo.deleteById(ubno);
		return "redirect:/user/user_communityList";
	}
	@RequestMapping("/user_positionMatch")
	public void userPositionMatch(HttpServletRequest request, Model model) {
		String uid = (String) request.getSession().getAttribute("id");
		Resume defResume = resumeRepo.findDefByUid(uid);
		Long rno = defResume.getRno();
		Hope hope = hopeRepo.findByUidAndRno(uid, rno);
		String job = hope.getJob();
		String region1 = hope.getRegion1();
		String sectors = hope.getSectors();
		List<JobAd> list = jobadRepo.findMatchingAd(region1,sectors,job);
        model.addAttribute("recentList", list);
	}
	@GetMapping("/user_resume_detailApply")
	public void userResumeDetail(HttpServletRequest request, Model model, @RequestParam("rno") Long rno) {
		String uid = (String) request.getSession().getAttribute("id");

		Optional<Resume> resumeOpt = resumeRepo.findResumeWithUserById(uid, rno);
		if (resumeOpt.isPresent()) {
			Resume resume = resumeOpt.get();
			model.addAttribute("resume", resume);

			User user = resume.getUser();
			model.addAttribute("user", user);

			List<Career> careerList = careerRepo.findByResumeRno(rno);
			model.addAttribute("careerList", careerList);

			List<Experience> experienceList = experienceRepo.findByResumeRno(rno);
			model.addAttribute("experienceList", experienceList);
			
			List<Hope> hopeList = hopeRepo.findByResumeRno(rno);
	        model.addAttribute("hopeList", hopeList);

	        List<License> licenseList = licenseRepo.findByResumeRno(rno);
	        model.addAttribute("licenseList", licenseList);

	        List<Oa> oaList = oaRepo.findByResumeRno(rno);
	        model.addAttribute("oaList", oaList);

	        List<SelfInfo> selfInfoList = selfinfoRepo.findByResumeRno(rno);
	        model.addAttribute("selfInfoList", selfInfoList);

	        List<School> schoolList = schoolRepo.findByResumeRno(rno);
	        model.addAttribute("schoolList", schoolList);
	        model.addAttribute("jno", request.getParameter("jno"));
		}
	}
	

	@PostMapping("/user_apply")
    public ResponseEntity<String> receiveData(HttpServletRequest request, @RequestBody Map<String, Object> payload) {
        String imgData = (String) payload.get("imgData");
        String sJno = (String) payload.get("jno");
        Long jno = Long.parseLong(sJno);
        String uid = (String) request.getSession().getAttribute("id");
		ApplyList al = new ApplyList();
		
		al.setJobAd(jobadRepo.findById(jno).get());
		al.setResume(resumeRepo.findById(jno).get());
		al.setCompany(jobadRepo.findById(jno).get().getCompany());
		al.setUser(userRepo.findById(uid).get());
		al.setStatus(0);
		al.setSave(imgData);
		applyRepo.save(al);

        return ResponseEntity.ok("Data received successfully");
    }
	

}
