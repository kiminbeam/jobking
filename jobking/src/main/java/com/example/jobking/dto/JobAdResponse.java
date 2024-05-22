package com.example.jobking.dto;

import java.util.List;

import com.example.jobking.entity.InterestCop;
import com.example.jobking.entity.JobAd;
import com.example.jobking.entity.JobScrap;

public class JobAdResponse {
    private List<JobAd> jobAdList;
    private List<JobScrap> jobScrapList;
    private List<InterestCop> interestCopList;

    // Constructor, getters and setters
    public JobAdResponse(List<JobAd> jobAdList, List<JobScrap> jobScrapList, List<InterestCop> interestCopList) {
        this.jobAdList = jobAdList;
        this.jobScrapList = jobScrapList;
        this.interestCopList = interestCopList;
    }

    public List<JobAd> getJobAdList() {
        return jobAdList;
    }

    public void setJobAdList(List<JobAd> jobAdList) {
        this.jobAdList = jobAdList;
    }

    public List<JobScrap> getJobScrapList() {
        return jobScrapList;
    }

    public void setJobScrapList(List<JobScrap> jobScrapList) {
        this.jobScrapList = jobScrapList;
    }

    public List<InterestCop> getInterestCopList() {
        return interestCopList;
    }

    public void setInterestCopList(List<InterestCop> interestCopList) {
        this.interestCopList = interestCopList;
    }
}