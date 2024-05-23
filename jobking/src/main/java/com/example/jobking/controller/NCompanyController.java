package com.example.jobking.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.jobking.entity.Career;
import com.example.jobking.entity.Company;
import com.example.jobking.entity.Hope;
import com.example.jobking.entity.InterestUser;
import com.example.jobking.entity.JobAd;
import com.example.jobking.entity.Resume;
import com.example.jobking.entity.User;
import com.example.jobking.repository.IApplyListRepository;
import com.example.jobking.repository.ICareerRepository;
import com.example.jobking.repository.ICompanyRepository;
import com.example.jobking.repository.IHopeRepository;
import com.example.jobking.repository.IInterestUserRepository;
import com.example.jobking.repository.IInterviewListRepository;
import com.example.jobking.repository.IJobAdRepository;
import com.example.jobking.repository.IResumeRepository;
import com.example.jobking.repository.IUserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/company")
@Controller
public class NCompanyController {

	@Autowired
	IJobAdRepository repository;

	@Autowired
	ICompanyRepository comrepository;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private ICareerRepository careerRepository;

	@Autowired
	private IResumeRepository resumeRepository;

	@Autowired
	private IHopeRepository hopeRepository;

	@Autowired
    private IInterestUserRepository interestUserRepository;
	
	@RequestMapping("/com_totalfind")
	public String totalFind(Model model, HttpSession session) {
		String companyId = (String) session.getAttribute("id");
		System.out.println(companyId);
	    List<InterestUser> scrappedUsers = interestUserRepository.findByCompanyCid(companyId); // InterestUser 객체 목록 조회
	    model.addAttribute("scrappedUsers", scrappedUsers); // 뷰로 전달
		List<User> allUsers = userRepository.findAll(); // 모든 유저 조회
		List<Hope> defaultHopes = new ArrayList<>();
		List<Resume> defaultResumes = new ArrayList<>();
		List<List<Career>> defaultCareers = new ArrayList<>(); // 각 이력서에 대한 경력 목록 리스트

		for (User user : allUsers) {
			Resume defaultResume = resumeRepository.findByUserAndDefAndDisclo(user, "1", "1");
			if (defaultResume != null) {
				Hope defaultHope = hopeRepository.findByUserAndResume(user, defaultResume);
				defaultHopes.add(defaultHope);
				defaultResumes.add(defaultResume); // 대표 이력서만 추가

				// 해당 이력서의 경력 목록 조회
				List<Career> careers = careerRepository.findByResume(defaultResume);
				defaultCareers.add(careers); // 경력 목록 리스트에 추가
			}
		}
		List<String> scrapUids = scrappedUsers.stream().map(iu -> iu.getUser().getUid()).collect(Collectors.toList());
	    model.addAttribute("scrapUids", scrapUids);
		
		long defaultResumeCount = resumeRepository.countByDef("1"); // 대표 이력서 갯수 조회
		model.addAttribute("defaultResumeCount", defaultResumeCount);

		model.addAttribute("defaultResumes", defaultResumes);
		model.addAttribute("defaultHopes", defaultHopes);
		model.addAttribute("defaultCareers", defaultCareers);

		return "company/com_totalfind";
	}
	
	@PostMapping("/scrap") 
    public String scrapResume(@RequestParam("uid") String userId, @RequestParam("rno") Long resumeId, HttpSession session) {
		String companyId = (String) session.getAttribute("id");
		Optional<InterestUser> existingScrap = interestUserRepository.findByUserUidAndCompanyCid(userId, companyId);

	    if (existingScrap.isPresent()) {
	        // 이미 스크랩된 경우 -> 스크랩 취소
	        interestUserRepository.delete(existingScrap.get()); // 스크랩 데이터 삭제
	    } else {
	        // 스크랩되지 않은 경우 -> 스크랩
	        InterestUser interestUser = InterestUser.builder()
	                .user(userRepository.findById(userId).orElseThrow())
	                .company(comrepository.findById(companyId).orElseThrow())
	                .resume(resumeRepository.findById(resumeId).orElseThrow())
	                .build();
	        interestUserRepository.save(interestUser);
	    }
        
        // 기존 페이지로 리다이렉트 (예: 이력서 목록 페이지)
        return "redirect:/company/com_totalfind"; 
    }

	@RequestMapping("/regi_jobadForm")
	public String regiForm(Model model) throws IOException {

		// job_category.json 데이터 읽어오기
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, Object>> jobCategories = objectMapper.readValue(
				new ClassPathResource("static/json/job_category.json").getFile(),
				new TypeReference<List<Map<String, Object>>>() {
				});
		model.addAttribute("jobCategories", jobCategories);

		// job.json 데이터 읽어오기
		List<Map<String, Object>> allJobs = objectMapper.readValue(
				new ClassPathResource("static/json/job.json").getFile(),
				new TypeReference<List<Map<String, Object>>>() {
				});
		model.addAttribute("allJobs", allJobs);

		// sector_category.json 데이터 읽어오기
		List<Map<String, Object>> sectorCategories = objectMapper.readValue(
				new ClassPathResource("static/json/sector_category.json").getFile(),
				new TypeReference<List<Map<String, Object>>>() {
				});

		// sector.json 데이터 읽어오기
		List<Map<String, Object>> allSectors = objectMapper.readValue(
				new ClassPathResource("static/json/sector.json").getFile(),
				new TypeReference<List<Map<String, Object>>>() {
				});

		// korea-administrative-district.json 데이터 읽어오기
		List<Map<String, Object>> regionData = objectMapper.readValue(
				new ClassPathResource("static/json/korea-administrative-district.json").getFile(),
				new TypeReference<List<Map<String, Object>>>() {
				});
		Map<String, List<String>> regions = regionData.stream()
				.collect(Collectors.toMap(map -> map.keySet().iterator().next(), // key 값 (서울특별시, 부산광역시 등)
						map -> (List<String>) map.values().iterator().next() // value 값 (구, 군 리스트)
				));

		// eduCode.json 데이터 읽어오기
		List<Map<String, Object>> eduCodes = objectMapper.readValue(
				new ClassPathResource("static/json/eduCode.json").getFile(),
				new TypeReference<List<Map<String, Object>>>() {
				});

		model.addAttribute("eduCodes", eduCodes);
		model.addAttribute("regions", regions);
		model.addAttribute("sectorCategories", sectorCategories);
		model.addAttribute("allSectors", allSectors);

		return "/company/regi_jobadForm";
	}

	@RequestMapping("/regi_jobad")
	public String regiAD( @RequestParam("jobCont") List<String> jobCont,
			@RequestParam("needskill") List<String> needskill, @RequestParam("srchKeywordNm") String srchKeywordNm,
			@RequestParam("startHour") int startHour, @RequestParam("startMinute") int startMinute,
			@RequestParam("endHour") int endHour, @RequestParam("endMinute") int endMinute,
			@ModelAttribute JobAd jobad, HttpSession session) {

		String cid = (String) session.getAttribute("id");

	    if (cid != null) { // cid가 null인 경우 예외 처리
	        Optional<Company> com = comrepository.findById(cid);

		if (com.isPresent()) {
			jobad.setCompany(com.get());
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				String jobContJson = objectMapper.writeValueAsString(jobCont);
				String needskillJson = objectMapper.writeValueAsString(needskill);

				// JobAd 객체에 JSON 문자열 설정
				jobad.setJobCont(jobContJson);
				jobad.setNeedskill(needskillJson);
				jobad.setSrchKeywordNm(srchKeywordNm);
			} catch (JsonProcessingException e) {
				// 예외 처리
			}

			Map<String, String> workTime = new HashMap<>();
			workTime.put("근무시작시간", String.format("%02d:%02d", startHour, startMinute));
			workTime.put("근무종료시간", String.format("%02d:%02d", endHour, endMinute));

			try {
				String workTimeJson = objectMapper.writeValueAsString(workTime);
				jobad.setWkdWkhCnt(workTimeJson); // WkdWkhCnt 필드에 JSON 저장
			} catch (JsonProcessingException e) {
				// 예외 처리
			}
			// minEdubglcd 값 설정 (변경된 부분)
			jobad.setMinEdubglcd(jobad.getMinEdubglcd());

			repository.save(jobad);
		}
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
		Optional<JobAd> option = repository.findById(jno);
		if (option.isPresent()) {
			JobAd jobad = option.get();
			model.addAttribute("detail", jobad);
			model.addAttribute("jobContList", jobad.getJobContList());
			model.addAttribute("needskillList", jobad.getNeedskillList());
			model.addAttribute("srchKeywordNmList", jobad.getSrchKeywordNmList());
			model.addAttribute("wkdWkhCntList", jobad.getWkdWkhCntList());

			return "/company/com_jobDetail";
		} else {
			// 해당하는 채용 공고가 없는 경우 처리
			return "redirect:/company/jobadList"; // 예시: 채용 공고 목록 페이지로 리다이렉트
		}
	}

	// 공고삭제
	@RequestMapping("/com_jobdelete")
	public String jobadDelete(@RequestParam("jno") Long jno) {

		repository.deleteById(jno);

		return "redirect:/company/jobadList";
	}

	// 공고수정폼
	@GetMapping("/com_modifyForm")
	public String jobadModifyForm(@RequestParam("jno") Long jno, Model model) throws IOException {

		Optional<JobAd> option = repository.findById(jno);
		if (option.isPresent()) {
			JobAd jobAd = option.get();
			model.addAttribute("modify", jobAd);
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				List<String> jobContList = objectMapper.readValue(jobAd.getJobCont(),
						new TypeReference<List<String>>() {
						});
				List<String> needskillList = objectMapper.readValue(jobAd.getNeedskill(),
						new TypeReference<List<String>>() {
						});
				List<String> srchKeywordNmList = objectMapper.readValue(jobAd.getSrchKeywordNm(),
						new TypeReference<List<String>>() {
						});

				model.addAttribute("jobContList", jobContList);
				model.addAttribute("needskillList", needskillList);
				model.addAttribute("srchKeywordNmList", srchKeywordNmList);
			} catch (JsonProcessingException e) {
			}

			// 근무 시간 JSON 파싱 및 시간, 분 추출
			String workTimeJson = jobAd.getWkdWkhCnt();
			if (workTimeJson != null) {
				try {
					Map<String, String> workTime = new ObjectMapper().readValue(workTimeJson, new TypeReference<>() {
					});
					String startTime = workTime.get("근무시작시간");
					String endTime = workTime.get("근무종료시간");
					model.addAttribute("startHour", startTime.split(":")[0]);
					model.addAttribute("startMinute", startTime.split(":")[1]);
					model.addAttribute("endHour", endTime.split(":")[0]);
					model.addAttribute("endMinute", endTime.split(":")[1]);
				} catch (JsonProcessingException e) {
					// JSON 파싱 오류 처리
				}
			}

			// job_category.json 데이터 읽어오기
			List<Map<String, Object>> jobCategories = objectMapper.readValue(
					new ClassPathResource("static/json/job_category.json").getFile(),
					new TypeReference<List<Map<String, Object>>>() {
					});
			model.addAttribute("jobCategories", jobCategories);

			// job.json 데이터 읽어오기
			List<Map<String, Object>> allJobs = objectMapper.readValue(
					new ClassPathResource("static/json/job.json").getFile(),
					new TypeReference<List<Map<String, Object>>>() {
					});
			model.addAttribute("allJobs", allJobs);

			// sector_category.json 데이터 읽어오기
			List<Map<String, Object>> sectorCategories = objectMapper.readValue(
					new ClassPathResource("static/json/sector_category.json").getFile(),
					new TypeReference<List<Map<String, Object>>>() {
					});

			// sector.json 데이터 읽어오기
			List<Map<String, Object>> allSectors = objectMapper.readValue(
					new ClassPathResource("static/json/sector.json").getFile(),
					new TypeReference<List<Map<String, Object>>>() {
					});

			// korea-administrative-district.json 데이터 읽어오기
			List<Map<String, Object>> regionData = objectMapper.readValue(
					new ClassPathResource("static/json/korea-administrative-district.json").getFile(),
					new TypeReference<List<Map<String, Object>>>() {
					});
			Map<String, List<String>> regions = regionData.stream()
					.collect(Collectors.toMap(map -> map.keySet().iterator().next(), // key 값 (서울특별시, 부산광역시 등)
							map -> (List<String>) map.values().iterator().next() // value 값 (구, 군 리스트)
					));

			// eduCode.json 데이터 읽어오기
			List<Map<String, Object>> eduCodes = objectMapper.readValue(
					new ClassPathResource("static/json/eduCode.json").getFile(),
					new TypeReference<List<Map<String, Object>>>() {
					});
			model.addAttribute("wkdWkhCntList", jobAd.getWkdWkhCntList2());
			model.addAttribute("eduCodes", eduCodes);
			model.addAttribute("regions", regions);
			model.addAttribute("sectorCategories", sectorCategories);
			model.addAttribute("allSectors", allSectors);

			model.addAttribute("modify", jobAd);

		}
		return "/company/com_modifyForm";

	}

	// 공고수정
	@RequestMapping("/com_modify")
	public String jobadModify(@RequestParam("jno") Long jno,
			@RequestParam(value = "jobCont", required = false) List<String> jobCont,
			@RequestParam(value = "needskill", required = false) List<String> needskill,
			@RequestParam("srchKeywordNm") String srchKeywordNm, @RequestParam("startHour") int startHour,
			@RequestParam("startMinute") int startMinute, @RequestParam("endHour") int endHour,
			@RequestParam("endMinute") int endMinute, @ModelAttribute JobAd jobad, HttpSession session) {

		
		String cid = (String) session.getAttribute("id");
		Optional<Company> com = comrepository.findById(cid);

		if (com.isPresent()) {
			jobad.setCompany(com.get());

			ObjectMapper objectMapper = new ObjectMapper();
			try {
				String jobContJson = objectMapper.writeValueAsString(jobCont);
				String needskillJson = objectMapper.writeValueAsString(needskill);

				// JobAd 객체에 JSON 문자열 설정
				jobad.setJobCont(jobContJson);
				jobad.setNeedskill(needskillJson);
				jobad.setSrchKeywordNm(srchKeywordNm);
			} catch (JsonProcessingException e) {
				// 예외 처리
			}

			Map<String, String> workTime = new HashMap<>();
			workTime.put("근무시작시간", String.format("%02d:%02d", startHour, startMinute));
			workTime.put("근무종료시간", String.format("%02d:%02d", endHour, endMinute));

			try {
				String workTimeJson = objectMapper.writeValueAsString(workTime);
				jobad.setWkdWkhCnt(workTimeJson); // WkdWkhCnt 필드에 JSON 저장
			} catch (JsonProcessingException e) {
				// 예외 처리
			}
			// minEdubglcd 값 설정
			jobad.setMinEdubglcd(jobad.getMinEdubglcd());

			repository.save(jobad);
		}

		return "redirect:/company/jobadList";
	}

}
