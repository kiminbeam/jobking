package com.example.jobking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.JobScrap;

public interface IJobScrapRepository extends JpaRepository<JobScrap, Long> {

	@Query(value="SELECT * FROM job_scrap where uid= :uid and jno = :jno", nativeQuery=true)
	Optional<JobScrap> findByUidNJno(@Param("uid") String uid,@Param("jno") Long jno);
	
	@Query(value="SELECT a.jno FROM jobad a right JOIN (SELECT * FROM job_scrap WHERE uid= :uid)  b ON a.jno=b.jno", nativeQuery=true)
	List<Long> findScrapedJobAdByUid(@Param("uid") String uid);
}
