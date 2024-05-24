package com.example.jobking.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.User;



public interface IUserRepository extends JpaRepository<User,String>  {
	
	@Query(value="select * from user where uid= :uid", nativeQuery = true)
	User findByUid(@Param("uid") String uid);
	
	// 1. 유저 회원 가입 수
    long count(); // JpaRepository 기본 메서드 사용

    
}

