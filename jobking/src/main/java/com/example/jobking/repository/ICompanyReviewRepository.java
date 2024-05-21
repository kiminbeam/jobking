package com.example.jobking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.CompanyReview;

public interface ICompanyReviewRepository extends JpaRepository<CompanyReview, Long> {
	
	@Query(value="select * from company_review where cid= :cid", nativeQuery = true)
	Optional <CompanyReview> findCpReview(@Param("cid") String cid);
}
