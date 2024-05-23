package com.example.jobking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.ApplyList;

public interface IApplyListRepository extends JpaRepository<ApplyList, Long>{
	
	@Query(value="select * from apply_list where jno= :jno", nativeQuery = true)
	 Optional <ApplyList> findByJno(@Param("jno") Long jno);
	
	@Query(value="select * from apply_list where uid= :uid AND jno= :jno", nativeQuery = true)
	ApplyList findByUidAndJno(@Param("uid") String uid, @Param("jno") Long jno);
	
	@Query(value="SELECT COUNT(*) FROM user a JOIN (select * from apply_list where jno= :jno ) b ON a.uid=b.uid where gender='F'", nativeQuery = true)
	int findFemaleAppli (@Param("jno") Long jno);
	
	@Query(value="SELECT COUNT(*) FROM user a JOIN (select * from apply_list where jno= :jno ) b ON a.uid=b.uid where gender='M'", nativeQuery = true)
	int findMaleAppli (@Param("jno") Long jno);
	
}
