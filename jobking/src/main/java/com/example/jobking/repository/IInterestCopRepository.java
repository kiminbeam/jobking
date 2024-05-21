package com.example.jobking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.InterestCop;
import com.example.jobking.entity.JobScrap;

public interface IInterestCopRepository extends JpaRepository<InterestCop, Long> {

	@Query(value="SELECT * FROM interest_cop where uid= :uid and cid = :cid", nativeQuery=true)
	Optional<InterestCop> findByUidNCid(@Param("uid") String uid,@Param("cid") String cid);
}
