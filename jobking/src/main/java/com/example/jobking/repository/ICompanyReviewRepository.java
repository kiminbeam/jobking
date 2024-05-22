package com.example.jobking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.CompanyReview;

public interface ICompanyReviewRepository extends JpaRepository<CompanyReview, Long> {
	
	@Query(value="select * from company_review where cid= :cid", nativeQuery = true)
	Optional <CompanyReview> findCpReview(@Param("cid") String cid);
	
	//평균 구하는 쿼리
	@Query(value="select ((sum(q1) + sum(q2) + sum(q3))/3) / count(distinct uid) from company_review where cid= :cid", nativeQuery = true)
	Double findAverageByCid(@Param("cid") String cid);
	
	
}
