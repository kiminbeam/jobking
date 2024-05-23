package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.InterestUser;

public interface IInterestUserRepository extends JpaRepository<InterestUser, Long> {
	
	
}
