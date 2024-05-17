package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.JobScrap;

public interface IJobScrapRepository extends JpaRepository<JobScrap, Long> {

}
