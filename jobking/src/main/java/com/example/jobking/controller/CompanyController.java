package com.example.jobking.controller;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.jobking.entity.Company;
import com.example.jobking.entity.JobAd;
import com.example.jobking.repository.ICompanyRepository;
import com.example.jobking.repository.IJobAdRepository;

@RequestMapping("/company")
@Controller
public class CompanyController {

	@Autowired
	IJobAdRepository repository;

	@Autowired
	ICompanyRepository comrepository;

	@RequestMapping("/")
	public String main() {
		return "/company/cpmain";
	}

	@RequestMapping("/regi_jobadForm")
	public String regiForm() {
		return "/company/regi_jobadForm";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/*
	@RequestMapping("/regi_jobad")
	public String regiAD(@ModelAttribute JobAd jobad, @RequestParam("receiptCloseDt") LocalDate receiptCloseDt) throws UnsupportedEncodingException {
		
		//로그인 미구현으로 임시 코딩
		//데이터베이스 기업 정보 저장 후 String cid에 해당 기업 cid 적어주면 데이터 들어갑니다.
		//*반드시 company테이블에 데이터가 있어야됨!
		String cid = "1";
		Optional<Company> com = comrepository.findById(cid);
		if (com.isPresent()) {
			jobad.setCompany(com.get());
			jobad.setReceiptCloseDt(receiptCloseDt);
			
			//jobad.setEmpEmail("default@example.com");
			//jobad.setEmpTel("010-0000-0000");
			//jobad.setEmpName("testname");
			//jobad.setEnterTpCd("testCD");
			//jobad.setMaxEdubglcd("1");
			jobad.setMltsvcExcHope("1");
			//jobad.setRegionCd("1");
			//jobad.setSalTpCd("1");
			//jobad.setMinEdubglcd("1");
			repository.save(jobad);
		}
		return "redirect:/company/jobadList";
	}
	*/

	@RequestMapping("/jobadList")
	public String jobadList() {
		return "/company/jobadList";
	}

}
