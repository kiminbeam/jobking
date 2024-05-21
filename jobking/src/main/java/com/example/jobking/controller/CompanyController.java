package com.example.jobking.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.jobking.dto.Humanlist;
import com.example.jobking.entity.ApplyList;
import com.example.jobking.entity.Company;
import com.example.jobking.entity.CompanyReview;
import com.example.jobking.entity.InterviewList;
import com.example.jobking.entity.JobAd;
import com.example.jobking.entity.User;
import com.example.jobking.entity.UserReview;
import com.example.jobking.repository.IApplyListRepository;
import com.example.jobking.repository.ICompanyRepository;
import com.example.jobking.repository.ICompanyReviewRepository;
import com.example.jobking.repository.IInterviewListRepository;
import com.example.jobking.repository.IJobAdRepository;
import com.example.jobking.repository.IUserReviewRepository;

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

	@RequestMapping("/cpmain")
	public String main() {
		return "/company/cpmain";
	}

	@RequestMapping("/regi_jobadForm")
	public String regiForm() {
		return "/company/regi_jobadForm";
	}

	
	 @RequestMapping("/regi_jobad") public String regiAD(@ModelAttribute JobAd jobad) {
	 
	 //로그인 미구현으로 임시 코딩 
	 //데이터베이스 기업 정보 저장 후 String cid에 해당 기업 cid 적어주면 데이터 들어갑니다.
	 //*반드시 company테이블에 데이터가 있어야됨! 
	 String cid = "aaaa"; 
	 Optional<Company> com = comrepository.findById(cid);
	 if (com.isPresent()) {
	 jobad.setCompany(com.get());
	  
	 //jobad.setEmpEmail("default@example.com");
	 //jobad.setEmpTel("010-0000-0000"); //jobad.setEmpName("testname");
	 //jobad.setEnterTpCd("testCD"); //jobad.setMaxEdubglcd("1");
	 jobad.setMltsvcExcHope("1"); //jobad.setRegionCd("1");
	 //jobad.setSalTpCd("1"); //jobad.setMinEdubglcd("1"); 
	 repository.save(jobad);
	 } 
	  
	 return "redirect:/company/jobadList"; 
	 }
	 

	@RequestMapping("/jobadList")
	public String jobadList(Model model) {

		List<JobAd> list = repository.findAll();

		model.addAttribute("list", list);

		return "/company/jobadList";
	}

	// 공고정보 상세보기
	@RequestMapping("/com_jobDetail")
	public String jobadDetail(@RequestParam("jno") Long jno, Model model) {
		// 채용공고 정보 불러오는 메서드 필요
		Optional<JobAd> option = repository.findById(jno);
		JobAd jobad = option.get();

		model.addAttribute("detail", jobad);

		return "/company/com_jobDetail";
	}

	// 공고삭제
	@RequestMapping("/com_jobdelete")
	public String jobadDelete(@RequestParam("jno") Long jno) {

		repository.deleteById(jno);

		return "redirect:/company/jobadList";
	}
	
	
	//공고수정폼
	@RequestMapping("/com_modifyForm")
	public String jobadModifyForm(@RequestParam("jno") Long jno, Model model) {
		
		
		Optional<JobAd> option = repository.findById(jno);
		JobAd jobad = option.get();
		
		model.addAttribute("modify", jobad);
		
		return "/company/com_modifyForm";
	}
	
	//공고수정
	@RequestMapping("/com_modify") 
	public String jobadModify(@RequestParam("jno") Long jno, @ModelAttribute JobAd jobad) {
		 //로그인 미구현으로 임시 코딩 
		 //데이터베이스 기업 정보 저장 후 String cid에 해당 기업 cid 적어주면 데이터 들어갑니다.
		 //*반드시 company테이블에 데이터가 있어야됨! 
		 String cid = "1"; Optional<Company> com = comrepository.findById(cid);
		 if (com.isPresent()) {
		 jobad.setCompany(com.get());
		 jobad.setJno(jno);
		 
		 repository.save(jobad);
		 
		 } 
		 
		 return "redirect:/company/jobadList"; 
		 }
	
	
	
	//지원자 리스트 불러오기
	@RequestMapping("/com_applicantList")
	public String applicantList (@RequestParam("cid") String cid, Model model) {
		//cid = "aaaa";
		
		
		//지원내역 테이블의 공고구분번호 데이터 가져오기
		List<ApplyList> list = applyListRepository.findAll();
		
		/*
		//공고 제목들이 들어간 리스트 jadtitle 
		List<Humanlist> hlist = null;
		
		Long jno = null;
		//공고제목구하는 for 문 
		for(int i = 0 ; i < list.size() ; i++) {
			
			//applylist 에서 불러온 모든 데이터들을 반복문을 이용해서 하나씩 꺼낸다.
			ApplyList applylist = list.get(i);
			
			//하나의 applylist객체에서 공고객체를 구한다.
			JobAd jad = applylist.getJobAd();
			
			//만약 위에서 구한 공고객체(jad)의 공고의 회사 아이디가 현재 세션의 cid와 같다면
			if(jad.getCompany().equals(cid)) {
				
				//공고의 제목을 구하고 난 뒤,
				String title = jad.getWantedTitle();
				
				//공고의 공고구분번호를 구해서
				jno = jad.getJno();
				
				// 위의 공고구분번호와 같은 공고구분번호를 가진 applylist의 데이터들을 구해낸다.
				Optional <ApplyList> jnolist = applyListRepository.findByJno(jno);
				
				jnolist.ifPresent(ApplyList -> {
					List <User> user = (List<User>) ApplyList.getUser();
					
					for(User u : user) {
						String username = u.getUname();
						String userid = u.getUid();
						Humanlist humanlist = new Humanlist(title, username, userid);
						hlist.add(humanlist);
					}
				});
				
				/*
				for()
				//구한 applylist 데이터들을 하나씩 확인한다.
				//*********************************
				for(int a : ) {
					ApplyList applist = jnolist.get(i);
					
					// 이 과정에서 하나의 applylist 객체 속 user 데이터를 꺼낸 뒤
					User user = applist.getUser();
					// 해당 user 데이터의 Uname 데이터를 꺼낸다.
					String username = user.getUname();
					
					// userid 는 페이지에서 해당 유저의 이름을 클릭했을때 
					// 해당유저의 상세페이지 a 태그에서 쓰일 userid이다
					String userid = user.getUid();

					// 꺼낸 Uname 데이터와 앞서 구한 공고 제목을 묶어서 데이터로 저장한다.
					Humanlist humanlist = new Humanlist(title, username, userid);
					hlist.add(humanlist);
					
				}
				
			}
		}
		*/
		model.addAttribute("hlist", list);
		
		return "/company/com_applicantList";
	}
	
	//입사 지원자 상세 페이지
	@RequestMapping("/com_resumeDetail")
	public String resumeDetail(@RequestParam("uid") String uid, @RequestParam("jno") Long jno,Model model) {
		ApplyList list = applyListRepository.findByUidAndJno(uid, jno);
		JobAd jobad = repository.findByJno(jno);
		
		User user = list.getUser();
		String saveSelfIntro = list.getSave();
		
		
		model.addAttribute("jobad", jobad);
		model.addAttribute("user", user);
		model.addAttribute("save", saveSelfIntro);
		
		return "/company/com_resumeDetail?uid=" + uid;
	}
	
	//면접자 합격여부 리스트 페이지
	@RequestMapping("/com_interviewList")
	public String interviewList(@RequestParam("cid") String cid, Model model) {
		Optional <InterviewList> interviewlist = interviewRepository.findByCid(cid);
		InterviewList interviewe = interviewlist.get();
		model.addAttribute("interview", interviewe);
		
		return "/company/com_interviewList";
	}
	
	
	@PostMapping("/changeInterview")
	@ResponseBody
	public String changeInterviewStatus(@RequestParam("interviewno") Long interviewno, @RequestParam("status")String newStatus) {
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
		}catch (Exception e){
			return "지원자 합격여부 변경 중 오류 발생.";
		}
	}
	
	@Autowired
	ICompanyReviewRepository cReviewRepository;
	
	@Autowired
	IUserReviewRepository uReviewRepository;
	
	@RequestMapping("/com_reviewList")
	public String findReviwer(@RequestParam("cid") String cid, Model model) {
		//기업이 유저에게 남긴 리뷰들 가져오는 기능
		Optional<CompanyReview> CPreviewlist = cReviewRepository.findCpReview(cid);
		//유저가 기업에게 남긴 리뷰를 가져오는 기능
		Optional<UserReview> Ureviewlist = uReviewRepository.findUserReview(cid);
		
		model.addAttribute("CPreview", CPreviewlist);
		model.addAttribute("Ureview", Ureviewlist);
		
		return "/company/com_reviewList?cid=" + cid;
	}
}
