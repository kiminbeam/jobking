package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.JobAd;

public interface IJobAdRepository extends JpaRepository<JobAd, Long> {

}
