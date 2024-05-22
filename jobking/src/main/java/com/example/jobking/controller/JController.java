package com.example.jobking.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.jobking.entity.Company;
import com.example.jobking.entity.CompanyReview;
import com.example.jobking.entity.InterestCop;
import com.example.jobking.entity.JobAd;
import com.example.jobking.entity.JobScrap;
import com.example.jobking.entity.OfferList;
import com.example.jobking.entity.Resume;
import com.example.jobking.entity.User;
import com.example.jobking.entity.UserBoard;
import com.example.jobking.entity.UserReply;
import com.example.jobking.entity.UserReview;
import com.example.jobking.repository.ICompanyRepository;
import com.example.jobking.repository.ICompanyReviewRepository;
import com.example.jobking.repository.IInterestCopRepository;
import com.example.jobking.repository.IJobAdRepository;
import com.example.jobking.repository.IJobScrapRepository;
import com.example.jobking.repository.IOfferListRepository;
import com.example.jobking.repository.IResumeRepository;
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
		return "/user/index";
	}
	
	@RequestMapping("/user_recruit_region")
	public void userRecruitRegion(Model model) {
		List<JobAd> jobAdList = jobadRepo.findAll();
		model.addAttribute("jobadList", jobAdList);
	}
	@RequestMapping("/user_recruit_regionSearch")
	public @ResponseBody List<JobAd> userRecruitRegionSearch(  @RequestParam(required = false, name = "region1Name") String region1Name,
	        @RequestParam(required = false, name = "region2Name") String region2Name,
	        @RequestParam(required = false, name = "sector1Name") String sector1Name,
	        @RequestParam(required = false, name = "sector2Name") String sector2Name,
	        @RequestParam(required = false, name = "position1Name") String position1Name,
	        @RequestParam(required = false, name = "position2Name") String position2Name) {
	
		 List<JobAd> list = jobadRepo.findJobad(region1Name, region2Name, sector1Name,sector2Name,position1Name,position2Name);
		 
		 //list를 json형태로 해서 화면단에 보여주고 화면단에서 뿌려주기
		System.out.println(list);
		System.out.println(list.size());
		return list;
	}

	@RequestMapping("/user_recruit_job")
	public void userRecruitJob() {
	}
	@RequestMapping("/user_recruit_sector")
	public void userRecruitSector() {
	}

	@RequestMapping("/user_recruitDetail")
	public void userRecruitDetail(HttpServletRequest request, @RequestParam("jno") Long jno, Model model) {
		String uid = (String) request.getSession().getAttribute("id");
		
		//해당 채용공고 정보 보내주기
		JobAd jobad = jobadRepo.findById(jno).get();
		model.addAttribute("jobad", jobad);
		System.out.println(jobad);
		//스크랩 여부 정보 보내주기
		Optional<JobScrap> checkS = jobscrapRepo.findByUidNJno(jno,uid);
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
	
	}
	@RequestMapping("/scrapJobad")
	public @ResponseBody String scrapJobad(HttpServletRequest request, @RequestParam("jno") String jno, Model model) {
		String uid = (String) request.getSession().getAttribute("id");
		Optional<JobScrap> check = jobscrapRepo.findByUidNJno(Long.parseLong(jno),uid);
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
		System.out.println(resumeList);
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
	@RequestMapping("/user_communityForm_insert")
	public void userCommunityFormInsert(UserBoard userBoard, Model model) {
		UserBoard latestAlertBoard = userBoardRepo.findLatestBoardByType("3");
		model.addAttribute("latestAlertBoard",latestAlertBoard);
	}
	@RequestMapping("/user_board_regist")
	public void userBoardRegist(HttpServletRequest request, UserBoard userBoard) {
		String uid = (String) request.getSession().getAttribute("id");
		userBoard.setUser(userRepo.findById(uid).get());
		userBoardRepo.save(userBoard);
	}
	@RequestMapping("/user_communityForm_edit")
	public void userCommunityFormEdit(@RequestParam("ubno") Long ubno, Model model) {
		UserBoard latestAlertBoard = userBoardRepo.findLatestBoardByType("3");
		model.addAttribute("latestAlertBoard",latestAlertBoard);
		
		UserBoard userBoard = userBoardRepo.findById(ubno).get();
		model.addAttribute(userBoard);
	}
	@RequestMapping("/user_communityForm_update")
	public void userCommunityFormUpdate(HttpServletRequest request, UserBoard userBoard, Model model) {
		UserBoard latestAlertBoard = userBoardRepo.findLatestBoardByType("3");
		model.addAttribute("latestAlertBoard",latestAlertBoard);
		
		String uid = (String) request.getSession().getAttribute("id");
		userBoard.setUser(userRepo.findById(uid).get());
		userBoardRepo.save(userBoard);
	}
	@RequestMapping("/user_communityForm_delete")
	public String userCommunityFormDelete(@RequestParam("ubno") Long ubno) {
		userBoardRepo.deleteById(ubno);
		return "redirect:/user/user_communityList";
	}
	
}
