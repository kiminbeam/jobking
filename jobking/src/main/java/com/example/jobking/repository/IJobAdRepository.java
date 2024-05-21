package com.example.jobking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.JobAd;

public interface IJobAdRepository extends JpaRepository<JobAd, Long> {
	
	@Query(value="SELECT * FROM jobAd where cid= :cid ORDER BY regDate DESC LIMIT 1;", nativeQuery=true)
	Optional<JobAd> findLatestJobAd(@Param("cid") String cid);
}
