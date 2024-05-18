package com.example.jobking.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

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
		return "cpmain";
	}
	
	@RequestMapping("/regi_jobadForm")
	public String regiForm() {
		return "regi_jobadForm";
	}
	

	@RequestMapping("/regi_jobad")
	public String regiAD() {
		//데이터 등록하는 메서드
		
		//등록한 채용공고 목록페이지
		return "redirect: jobadList";
	}

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
	
	/*
	@RequestMapping("/regi_jobad")
    public String regiAD(@ModelAttribute JobAd jobad, @RequestParam("receiptCloseDt") Date receiptCloseDt, @RequestParam("cid") String cid) { 
        
		Optional com = comrepository.findById(cid);
		cid.getClass();
		jobad.setReceiptCloseDt(receiptCloseDt); 
        jobad = JobAd.builder().company(cid.getCid()).sectors(jobad.getSectors()).wantedTitle(jobad.getWantedTitle()).position(jobad.getPosition()).jobCont(jobad.getJobCont()).receiptCloseDt(receiptCloseDt).empTpNm(jobad.getEmpTpNm()).collectPsncnt(jobad.getCollectPsncnt()).salTpNm(jobad.getSalTpNm()).minEdubglcd(jobad.getMinEdubglcd()).maxEdubglcd(jobad.getMaxEdubglcd()).rcptMthd(jobad.getRcptMthd()).regionCd(jobad.getRegionCd()).WkdWkhCnt(jobad.getWkdWkhCnt()).etcWelfare(jobad.getEtcWelfare()).enterTpCd(jobad.getEnterTpCd()).salTpCd(jobad.getSalTpCd()).empName("sdf").empTel("123").empEmail("213123").build();
        repository.save(jobad);
        return "redirect:/jobadList";
    }
	*/
	

	
	@RequestMapping("/jobadList")
	public String jobadList() {
		return "jobadList";
	}
	
}
