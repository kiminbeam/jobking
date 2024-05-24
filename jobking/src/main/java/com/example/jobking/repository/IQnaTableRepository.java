package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.QnaTable;

public interface IQnaTableRepository extends JpaRepository<QnaTable, Long> {
	
    // 유저 문의 수
    long countByUserUidIsNotNull(); // uid가 null이 아닌(유저 문의) 경우 카운트

    // 기업 문의 수
    long countByCompanyCidIsNotNull(); // cid가 null이 아닌(기업 문의) 경우 카운트
 
    
}
