package com.example.jobking.dto;

import com.example.jobking.entity.JobAd;

public class JobAdWithScrapAndLike {
	private JobAd jobad;
	private boolean isScrapped; // 추가 필드
	
	public JobAd getJobad() {
		return jobad;
	}
	public void setJobad(JobAd jobad) {
		this.jobad = jobad;
	}
	public boolean isScrapped() {
		return isScrapped;
	}
	public void setScrapped(boolean isScrapped) {
		this.isScrapped = isScrapped;
	}
	
}
