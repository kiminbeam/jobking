package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.Hope;
import com.example.jobking.entity.Resume;
import com.example.jobking.entity.User;

public interface IHopeRepository extends JpaRepository<Hope, Long> {

	
	// 유저와 이력서를 기준으로 희망 조건 조회
    Hope findByUserAndResume(User user, Resume resume);
}
