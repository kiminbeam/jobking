package com.example.jobking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.ApplyList;

public interface IUserApplyListRepository extends JpaRepository<ApplyList, Long>{
	@Query(value="select * from apply_list where uid= uid and status=2", nativeQuery = true)
	public List<ApplyList> findByUid(@Param("uid") String uid);
}
