package com.example.jobking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.InterestCop;

public interface IInterestCopRepository extends JpaRepository<InterestCop, Long> {

	@Query(value="SELECT * FROM interest_cop where uid= :uid and cid = :cid", nativeQuery=true)
	Optional<InterestCop> findByUidNCid(@Param("uid") String uid,@Param("cid") String cid);
	
	@Query(value="SELECT * FROM interest_cop where uid= :uid", nativeQuery=true)
	List<InterestCop> findByUid(@Param("uid") String uid);

}
