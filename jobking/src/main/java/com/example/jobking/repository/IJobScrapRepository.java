package com.example.jobking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.JobScrap;

public interface IJobScrapRepository extends JpaRepository<JobScrap, Long> {

	@Query(value="SELECT * FROM job_scrap where uid= :uid and jno = :jno", nativeQuery=true)
	Optional<JobScrap> findByUidNJno(@Param("jno") Long jno, @Param("uid") String uid);
}
