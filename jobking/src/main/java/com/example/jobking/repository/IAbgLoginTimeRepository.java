package com.example.jobking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.AbgLoginTime;
import com.example.jobking.entity.JobAd;

public interface IAbgLoginTimeRepository extends JpaRepository<AbgLoginTime, Long> {

	
	@Query(value="SELECT * FROM avg_login_time where uid= :uid order by logno desc LIMIT 1;", nativeQuery=true)
	Optional<AbgLoginTime> findLatestAbgLoginTime(@Param("uid") String uid);
}
