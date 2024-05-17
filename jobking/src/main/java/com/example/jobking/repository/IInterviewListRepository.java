package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.InterviewList;

public interface IInterviewListRepository extends JpaRepository<InterviewList, Long> {

}
