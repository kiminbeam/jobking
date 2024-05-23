package com.example.jobking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.Oa;

public interface IOaRepository extends JpaRepository<Oa, Long> {
	
	List<Oa> findByResumeRno(Long rno);

	@Query(value="delete from oa where ono = :ono" , nativeQuery=true)
	public void deleteOaByOno(@Param("ono") Long ono);
 
	void deleteByResumeRno(Long rno);
}
