package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.Company;

public interface ICompanyRepository extends JpaRepository<Company,String> {
	
	@Query(value="select * from company where cid= :cid", nativeQuery = true)
	Company findByCid(@Param("cid") String cid);
	
}
