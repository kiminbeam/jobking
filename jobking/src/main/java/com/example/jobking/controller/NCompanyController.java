package com.example.jobking.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.jobking.entity.Career;
import com.example.jobking.entity.Company;
import com.example.jobking.entity.Hope;
import com.example.jobking.entity.JobAd;
import com.example.jobking.entity.Resume;
import com.example.jobking.entity.User;
import com.example.jobking.repository.IApplyListRepository;
import com.example.jobking.repository.ICareerRepository;
import com.example.jobking.repository.ICompanyRepository;
import com.example.jobking.repository.IHopeRepository;
import com.example.jobking.repository.IInterviewListRepository;
import com.example.jobking.repository.IJobAdRepository;
import com.example.jobking.repository.IResumeRepository;
import com.example.jobking.repository.IUserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	/*
	@RequestMapping("com_totalfind")
	public String totalFind(Model model) {
		
		List<User> allUsers = userRepository.findAll(); // 모든 유저 조회
		List<Hope> defaultHopes = new ArrayList<>();
        List<Resume> defaultResumes = new ArrayList<>();
        List<List<Career>> defaultCareers = new ArrayList<>(); // 각 이력서에 대한 경력 목록 리스트

        for (User user : allUsers) {
            Resume defaultResume = resumeRepository.findByUserAndDef(user, "1");
            if (defaultResume != null) {
            	Hope defaultHope = hopeRepository.findByUserAndResume(user, defaultResume);
            	defaultHopes.add(defaultHope);
                defaultResumes.add(defaultResume); // 대표 이력서만 추가
                
             // 해당 이력서의 경력 목록 조회
                List<Career> careers = careerRepository.findByResume(defaultResume);
                defaultCareers.add(careers); // 경력 목록 리스트에 추가
            }
        }
        
        long defaultResumeCount = resumeRepository.countByDef("1"); // 대표 이력서 갯수 조회
        model.addAttribute("defaultResumeCount", defaultResumeCount);

        model.addAttribute("defaultResumes", defaultResumes);
        model.addAttribute("defaultHopes", defaultHopes);
        model.addAttribute("defaultCareers", defaultCareers);
		
		return "company/com_totalfind";
	}
	*/
	
    

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
	public String regiAD(@RequestParam("jobCont") List<String> jobCont,
			@RequestParam("needskill") List<String> needskill, @RequestParam("srchKeywordNm") String srchKeywordNm,
			@RequestParam("startHour") int startHour, @RequestParam("startMinute") int startMinute,
			@RequestParam("endHour") int endHour, @RequestParam("endMinute") int endMinute,
			@ModelAttribute JobAd jobad) {

		// 로그인 미구현으로 임시 코딩
		// 데이터베이스 기업 정보 저장 후 String cid에 해당 기업 cid 적어주면 데이터 들어갑니다.
		// *반드시 company테이블에 데이터가 있어야됨!
		String cid = "1";
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
	            List<String> jobContList = objectMapper.readValue(jobAd.getJobCont(), new TypeReference<List<String>>() {});
	            List<String> needskillList = objectMapper.readValue(jobAd.getNeedskill(), new TypeReference<List<String>>() {});
	            List<String> srchKeywordNmList = objectMapper.readValue(jobAd.getSrchKeywordNm(), new TypeReference<List<String>>() {});

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
		    @RequestParam("srchKeywordNm") String srchKeywordNm,
			@RequestParam("startHour") int startHour, @RequestParam("startMinute") int startMinute,
			@RequestParam("endHour") int endHour, @RequestParam("endMinute") int endMinute,
			@ModelAttribute JobAd jobad) {

		// 로그인 미구현으로 임시 코딩
		// 데이터베이스 기업 정보 저장 후 String cid에 해당 기업 cid 적어주면 데이터 들어갑니다.
		// *반드시 company테이블에 데이터가 있어야됨!
		String cid = "1";
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
