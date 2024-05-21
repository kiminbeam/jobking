package com.example.jobking.entity;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Slf4j
@Table(name = "jobad")
public class JobAd extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "jno")
	private Long jno;

	@ManyToOne
	@JoinColumn(name = "cid", referencedColumnName = "cid", nullable = false)
	private Company company;

	@Column(name = "sector1", nullable = false, length = 30)
	private String sector1;
	
	@Column(name = "sector2", nullable = false, length = 30)
	private String sector2;

	@Column(name = "wantedTitle", nullable = false, length = 50)
	private String wantedTitle;

	@Column(name = "position1", nullable = false, length = 30)
	private String position1;
	
	@Column(name = "position2", nullable = false, length = 30)
	private String position2;

	@Column(name = "jobCont", nullable = false, columnDefinition = "TEXT")
	private String jobCont;
	
	@Column(name = "yofExperiences", length = 10)
	private String yofExperiences;
	
	@Column(name = "receiptCloseDt", nullable = false)
	private LocalDate receiptCloseDt;

	@Column(name = "empTpNm", nullable = false, length = 30)
	private String empTpNm;

	@Column(name = "collectPsncnt", nullable = false)
	private int collectPsncnt;

	@Column(name = "salTpNm", nullable = false, length = 30)
	private String salTpNm;

	@Column(name = "minEdubglcd", nullable = false, length = 10)
	private String minEdubglcd;

	@Column(name = "maxEdubglcd", nullable = false, length = 10)
	private String maxEdubglcd;

	@Column(name = "mltsvcExcHope", nullable = false, length = 10)
	private String mltsvcExcHope;

	@Column(name = "needskill", length = 20)
	private String needskill;

	@Column(name = "rcptMthd", length = 10)
	private String rcptMthd;

	@Column(name = "region1", nullable = false, length = 10)
	private String region1;
	
	@Column(name = "region2", nullable = false, length = 10)
	private String region2;

	@Column(name = "WkdWkhCnt", nullable = false, length = 2)
	private String WkdWkhCnt;

	@Column(name = "retirepay", length = 30)
	private String retirepay;

	@Column(name = "etcWelfare", nullable = false, columnDefinition = "TEXT")
	private String etcWelfare;

	@Column(name = "attachFileUrl", length = 30)
	private String attachFileUrl;

	@Column(name = "attachFileUrl2", length = 50, columnDefinition = "VARCHAR(50)")
	private String attachFileUrl2;

	@Column(name = "srchKeywordNm", length = 30)
	private String srchKeywordNm;

	@Column(name = "enterTpCd", nullable = false, length = 20, columnDefinition = "VARCHAR(20)")
	private String enterTpCd;

	@Column(name = "salTpCd", nullable = false, length = 20, columnDefinition = "VARCHAR(20)")
	private String salTpCd;

	@Column(name = "empName", nullable = false, length = 30)
	private String empName;

	@Column(name = "empEmail", nullable = false, length = 40)
	private String empEmail;

	@Column(name = "empTel", nullable = false, length = 20)
	private String empTel;
	
	public List<String> getJobContList() {
	    try {
	        return new ObjectMapper().readValue(jobCont, new TypeReference<List<String>>() {});
	    } catch (JsonProcessingException e) {
	        log.error("Error parsing jobCont JSON: {}", e.getMessage());
	        return Collections.emptyList(); // or throw an exception
	    }
	}

	public List<String> getNeedskillList() {
	    try {
	        return new ObjectMapper().readValue(needskill, new TypeReference<List<String>>() {});
	    } catch (JsonProcessingException e) {
	        log.error("Error parsing needskill JSON: {}", e.getMessage());
	        return Collections.emptyList(); // or throw an exception
	    }
	}

	public List<String> getSrchKeywordNmList() {
	    try {
	        return new ObjectMapper().readValue(srchKeywordNm, new TypeReference<List<String>>() {});
	    } catch (JsonProcessingException e) {
	        log.error("Error parsing srchKeywordNm JSON: {}", e.getMessage());
	        return Collections.emptyList(); // or throw an exception
	    }
	}
	
}
