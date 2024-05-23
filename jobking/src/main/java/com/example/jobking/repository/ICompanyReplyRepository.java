package com.example.jobking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.CompanyReply;
import com.example.jobking.entity.CompanyReview;

public interface ICompanyReplyRepository extends JpaRepository<CompanyReview, Long> {

	@Query(value="delete from company_reply where cbno= :cbno",nativeQuery=true)
	void deleteAllByCbno(@Param("cbno") Long cbno);
	
	@Query(value="select * from company_reply where cbno= :cbno",nativeQuery=true)
	List<CompanyReply> findAllByCbno(@Param("cbno") Long cbno);
}
