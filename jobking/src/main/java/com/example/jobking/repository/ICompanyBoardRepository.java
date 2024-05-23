package com.example.jobking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.CompanyBoard;

public interface ICompanyBoardRepository extends JpaRepository<CompanyBoard, Long> {
	
	@Query(value="select * from company_board where type= :type",nativeQuery=true)
	List<CompanyBoard> findAllByType(@Param("type") String type);
	
	@Query(value="select * from company_board where type= :type order by regdate desc limit 1", nativeQuery=true)
	CompanyBoard findLatestBoardByType(@Param("type") String type);
}
