package com.example.jobking.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.jobking.entity.ApplyList;
import com.example.jobking.entity.Company;
import com.example.jobking.entity.CompanyBoard;
import com.example.jobking.entity.CompanyReply;
import com.example.jobking.entity.CompanyReview;
import com.example.jobking.entity.InterviewList;
import com.example.jobking.entity.JobAd;
import com.example.jobking.entity.User;
import com.example.jobking.entity.UserReview;
import com.example.jobking.repository.IApplyListRepository;
import com.example.jobking.repository.ICompanyBoardRepository;
import com.example.jobking.repository.ICompanyReplyRepository;
import com.example.jobking.repository.ICompanyRepository;
import com.example.jobking.repository.ICompanyReviewRepository;
import com.example.jobking.repository.IInterviewListRepository;
import com.example.jobking.repository.IJobAdRepository;
import com.example.jobking.repository.IUserRepository;
import com.example.jobking.repository.IUserReviewRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/company")
@Controller
public class CompanyController {

	@Autowired
	IJobAdRepository repository;

	@Autowired
	ICompanyRepository comrepository;

	@Autowired
	IApplyListRepository applyListRepository;

	@Autowired
	IInterviewListRepository interviewRepository;
	
	
	@RequestMapping("/pageTest")
	public String pageTest() {
		return "/company/pageTest";
	}
	

	
	
	@RequestMapping("/cpmain")
	public String main() {
		return "/company/cpmain";
	}

	
	// 지원자 리스트 불러오기
		@RequestMapping("/com_applicantList")
		public String applicantList(@RequestParam("cid") String cid, Model model) {
			// cid = "aaaa";

			// 지원내역 테이블의 공고구분번호 데이터 가져오기
			List<ApplyList> list = applyListRepository.findAll();

			/*
			 * //공고 제목들이 들어간 리스트 jadtitle List<Humanlist> hlist = null;
			 * 
			 * Long jno = null; //공고제목구하는 for 문 for(int i = 0 ; i < list.size() ; i++) {
			 * 
			 * //applylist 에서 불러온 모든 데이터들을 반복문을 이용해서 하나씩 꺼낸다. ApplyList applylist =
			 * list.get(i);
			 * 
			 * //하나의 applylist객체에서 공고객체를 구한다. JobAd jad = applylist.getJobAd();
			 * 
			 * //만약 위에서 구한 공고객체(jad)의 공고의 회사 아이디가 현재 세션의 cid와 같다면
			 * if(jad.getCompany().equals(cid)) {
			 * 
			 * //공고의 제목을 구하고 난 뒤, String title = jad.getWantedTitle();
			 * 
			 * //공고의 공고구분번호를 구해서 jno = jad.getJno();
			 * 
			 * // 위의 공고구분번호와 같은 공고구분번호를 가진 applylist의 데이터들을 구해낸다. Optional <ApplyList>
			 * jnolist = applyListRepository.findByJno(jno);
			 * 
			 * jnolist.ifPresent(ApplyList -> { List <User> user = (List<User>)
			 * ApplyList.getUser();
			 * 
			 * for(User u : user) { String username = u.getUname(); String userid =
			 * u.getUid(); Humanlist humanlist = new Humanlist(title, username, userid);
			 * hlist.add(humanlist); } });
			 * 
			 * /* for() //구한 applylist 데이터들을 하나씩 확인한다. //*********************************
			 * for(int a : ) { ApplyList applist = jnolist.get(i);
			 * 
			 * // 이 과정에서 하나의 applylist 객체 속 user 데이터를 꺼낸 뒤 User user = applist.getUser(); //
			 * 해당 user 데이터의 Uname 데이터를 꺼낸다. String username = user.getUname();
			 * 
			 * // userid 는 페이지에서 해당 유저의 이름을 클릭했을때 // 해당유저의 상세페이지 a 태그에서 쓰일 userid이다 String
			 * userid = user.getUid();
			 * 
			 * // 꺼낸 Uname 데이터와 앞서 구한 공고 제목을 묶어서 데이터로 저장한다. Humanlist humanlist = new
			 * Humanlist(title, username, userid); hlist.add(humanlist);
			 * 
			 * }
			 * 
			 * } }
			 */
			model.addAttribute("hlist", list);

			return "/company/com_applicantList";
		}
	/*
		// 입사 지원자 상세 페이지
		@RequestMapping("/com_resumeDetail")
		public String resumeDetail(@RequestParam("uid") String uid, @RequestParam("jno") Long jno, Model model) {
			ApplyList list = applyListRepository.findByUidAndJno(uid, jno);
			JobAd jobad = repository.findByJno(jno);

			User user = list.getUser();
			String saveSelfIntro = list.getSave();

			model.addAttribute("jobad", jobad);
			model.addAttribute("user", user);
			model.addAttribute("save", saveSelfIntro);

			return "/company/com_resumeDetail?uid=" + uid;
		}
	*/
		// 면접자 합격여부 리스트 페이지
		@RequestMapping("/com_interviewList")
		public String interviewList(@RequestParam("cid") String cid, Model model) {
			List<InterviewList> interviewlist = interviewRepository.findByCid(cid);
			//InterviewList interviewe = interviewlist.get();
			model.addAttribute("interview", interviewlist);

			return "/company/com_interviewList";
		}

		@PostMapping("/changeInterview")
		@ResponseBody
		public String changeInterviewStatus(@RequestParam("interviewno") Long interviewno,
				@RequestParam("status") String newStatus) {
			try {
				interviewRepository.updateInterview(newStatus, interviewno);
				return "면접 상태가 성공적으로 변경되었습니다.";
			} catch (Exception e) {
				return "면접 상태 변경 중 오류 발생.";
			}
		}

		@PostMapping("/updatePassStatus")
		@ResponseBody
		public String updatePassStatus(@RequestParam("interviewno") Long interviewno, @RequestParam("pass") String pass) {

			try {
				interviewRepository.updatePass(pass, interviewno);
				return "지원자의 합격여부가 성공적으로 변경되었습니다.";
			} catch (Exception e) {
				return "지원자 합격여부 변경 중 오류 발생.";
			}
		}

		@Autowired
		ICompanyReviewRepository cReviewRepository;

		@Autowired
		IUserReviewRepository uReviewRepository;
		
		//구직자에게 받은 기업 평점 + 지원자 평가 평점 보는 페이지
		@RequestMapping("/com_reviewList")
		public String findReviwer(@RequestParam("cid") String cid, Model model) {
			// 기업이 유저에게 남긴 리뷰들 가져오는 기능
			Optional<CompanyReview> CPreviewlist = cReviewRepository.findCpReview(cid);
			// 유저가 기업에게 남긴 리뷰를 가져오는 기능
			Optional<UserReview> Ureviewlist = uReviewRepository.findUserReview(cid);

			model.addAttribute("CPreview", CPreviewlist);
			model.addAttribute("Ureview", Ureviewlist);

			return "/company/com_reviewList?cid=" + cid;
		}
		
		
		@RequestMapping("/com_reviewForm")
		public String reviewForm(@RequestParam("uid") String uid, Model model) {
			
			model.addAttribute("uid", uid);
			return "/company/com_reviewForm";
		}
		
		@Autowired
		IUserRepository userRepository;
		
		@RequestMapping("/saveUserReview")
		public String saveUserReview(
				@RequestParam("q1") String q1,
				@RequestParam("q2") String q2,
				@RequestParam("q3") String q3,
				@RequestParam("feedback") String feedback,
				HttpServletRequest request) {
			
			//구현 단계 기업 로그인 기능 구현 X 
			String cid = (String)request.getSession().getAttribute("id");
			Company com = comrepository.findByCid(cid);
			
			String uid = request.getParameter("uid");
			User user = userRepository.findByUid(uid);
			
			CompanyReview review = new CompanyReview();
			review.setCompany(com);
			review.setUser(user);
			review.setQ1(q1);
			review.setQ2(q2);
			review.setQ3(q3);
			review.setFeedback(feedback);
			
			
			cReviewRepository.save(review);
			return "redirect: /company/com_reviewList";
		}
		
		// 회사가 로그인 되어있는 상태에서 cid를 받아올려면 servletRequest 사용?? 
		@RequestMapping("/com_info")
		public String comInfo(@RequestParam("cid") String cid, Model model) {
			
			//String cid = (String)request.getSession().getAttribute("id");
			
			Company com = comrepository.findByCid(cid);
			
			model.addAttribute("com", com);
			
			return "/company/com_info";
		}
		
		@RequestMapping("/com_infoMod")
		public String comInfoMod(HttpServletRequest request, Model model) {
			String cid = (String)request.getSession().getAttribute("id");
			Company com = comrepository.findByCid(cid);
			
			model.addAttribute("com", com);
			
			return "/company/com_infoMod";
		}
		
		@RequestMapping("/updateComInfo")
		public String updateComInfo(HttpServletRequest request,@ModelAttribute Company company) {
			//Company company = comrepository.findPW(cid);
			//String cpw = company.getCpw();
			String cid = (String)request.getSession().getAttribute("id");
			
			comrepository.save(company);
			
			return "redirect:/company/com_info?cid=" + cid;
		}
		
		@Autowired
		ICompanyBoardRepository comboardRepository; 
		
		@RequestMapping("/com_communityList")
		public String communityList(Model model) {
			List<CompanyBoard> allList = comboardRepository.findAll();
			List<CompanyBoard> t1List = comboardRepository.findAllByType("1");
			List<CompanyBoard> t2List = comboardRepository.findAllByType("2");
			List<CompanyBoard> t3List = comboardRepository.findAllByType("3");
			
			CompanyBoard latestAlertBoard = comboardRepository.findLatestBoardByType("3");
			
			model.addAttribute("allList", allList);
			model.addAttribute("t1List", t1List);
			model.addAttribute("t2List", t2List);
			model.addAttribute("t3List", t3List);
			model.addAttribute("latestAlertBoard", latestAlertBoard);
			
			return "/company/com_communityList";
		}
		
		@Autowired
		ICompanyReplyRepository comReplyRepository; 
		
		@RequestMapping("/com_communityDetail")
		public String communityDetail(@RequestParam("cbno") Long cbno, Model model) {
			CompanyBoard companyBoard = comboardRepository.findById(cbno).get(); 
			
			CompanyBoard latestAlertBoard = comboardRepository.findLatestBoardByType("3");
			model.addAttribute("latestAlertBoard", latestAlertBoard);
			
			model.addAttribute("companyBoard", companyBoard);
			
			List<CompanyReply> companyReplyList = comReplyRepository.findAllByCbno(cbno);
			model.addAttribute("companyReplyList", companyReplyList);
			
			return "/company/com_communityDetail";
		}
		
		//댓글 작성
		@RequestMapping("/companyReply_insert")
		public String insertComReply(@RequestParam("cbno") Long cbno, @RequestParam("cid") String cid, CompanyReply comReply,HttpServletRequest request) {
			Optional<CompanyBoard> list= comboardRepository.findById(cbno);
			CompanyBoard board = list.get();
			
			Optional<Company> com = comrepository.findById(cid);
			Company company = com.get();
			
			comReply.setCompanyBoard(board);
			comReply.setCompany(company);
			
			comReplyRepository.save(comReply);
			return "redirect:/company/com_communityDetail?cbno=" + cbno;
		}
		
		@RequestMapping("/com_communityForm_insert")
		public String comCommunityFormInsert(CompanyBoard comboard, Model model) {
			CompanyBoard latestAlertBoard = comboardRepository.findLatestBoardByType("3");
			model.addAttribute("latestAlertBoard", latestAlertBoard);
			
			return "/company/com_communityForm_insert";
		}
		
		@RequestMapping("/com_boardRegist")
		public String comBoardRegist(HttpServletRequest request, CompanyBoard companyBoard) {
			String cid = (String) request.getSession().getAttribute("id");
			companyBoard.setCompany(comrepository.findById(cid).get());
			comboardRepository.save(companyBoard);
			
			return "/company/com_communityList";
		}
		
		@RequestMapping("/com_communityForm_edit")
		public String comBoardEdit(@RequestParam("cbno") Long cbno, Model model) {
			CompanyBoard latestAlertBoard = comboardRepository.findLatestBoardByType("3");
			model.addAttribute("latestAlertBoard", latestAlertBoard);
			
			CompanyBoard companyBoard = comboardRepository.findById(cbno).get();
			model.addAttribute("companyBoard", companyBoard);
			
			return "/company/com_communityForm_edit";
		}
		
		@RequestMapping("/com_boardUpdate")
		public String comBoardUpdate(HttpServletRequest request, CompanyBoard comboard, Model model) {
			
			CompanyBoard latestAlertBoard = comboardRepository.findLatestBoardByType("3");
			model.addAttribute("latestAlertBoard", latestAlertBoard);
			
			String cid = (String)request.getSession().getAttribute("id");
			comboard.setCompany(comrepository.findById(cid).get());
			comboardRepository.save(comboard);
			
			return "/company/com_communityForm_update";
		}
		
		@RequestMapping("/com_communityForm_delete")
		public String comBoardDelete(@RequestParam("cbno") Long cbno) {
			comboardRepository.deleteById(cbno);
			
			return "redirect:/company/com_communityList";
		}
		
		@RequestMapping("/com_myBoardList")
		public String comMyBoardList(HttpServletRequest request, Model model) {
			String cid = (String) request.getSession().getAttribute("id");
			List<CompanyBoard> comBoardList = comboardRepository.findByCid(cid);
			List<CompanyReply> comReplyList = comReplyRepository.findAllByCid(cid);
			
			model.addAttribute("comBoardList", comBoardList);
			model.addAttribute("comReplyList", comReplyList);
			
			System.out.println(comReplyList);
			
			return "/company/com_myboardList";
		}
		
		@RequestMapping("/delete_board")
		public String deleteBoard(@RequestParam("cbno")Long cbno,HttpServletRequest request) {
			
			comReplyRepository.deleteAllByCbno(cbno);
			
			comboardRepository.delete(comboardRepository.findById(cbno).get());
			
			return "/company/com_myboardList";
		}
		
		@RequestMapping("/delete_reply")
		public String delete_reply(@RequestParam("relyno") Long replyno, Model model) {
			comReplyRepository.delete(comReplyRepository.findById(replyno).get());
			
			return "/company/com_myboardList";
		}
		
		
	}


