package com.example.jobking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.JobAd;

public interface IJobAdRepository extends JpaRepository<JobAd, Long> {
	
	@Query(value="SELECT * FROM jobAd where cid= :cid ORDER BY regDate DESC LIMIT 1;", nativeQuery=true)
	Optional<JobAd> findLatestJobAd(@Param("cid") String cid);
	
	
	 @Query("SELECT ja FROM JobAd ja WHERE " +
	           "(:region1 IS NULL OR ja.region1 = :region1) AND " +
	           "(:region2 IS NULL OR ja.region2 = :region2) AND " +
	           "(:sector1 IS NULL OR ja.sector1 = :sector1) AND " +
	           "(:sector2 IS NULL OR ja.sector2 = :sector2) AND " +
	           "(:position1 IS NULL OR ja.position1 = :position1) AND " +
	           "(:position2 IS NULL OR ja.position2 = :position2)")
	    List<JobAd> findJobad(@Param("region1") String region1,
	                                   @Param("region2") String region2,
	                                   @Param("sector1") String sector1,
	                                   @Param("sector2") String sector2,
	                                   @Param("position1") String position1,
	                                   @Param("position2") String position2);
}
