package com.example.jobking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.OfferList;

public interface IOfferListRepository extends JpaRepository<OfferList, Long> {
	@Query(value="select * from offer_list where uid= uid  and status=2", nativeQuery = true)
	public List<OfferList> findByUid(@Param("uid") String uid);

	@Query(value="select * from offer_list where uid= :uid", nativeQuery=true)
	List<OfferList> findAllByUid(@Param("uid") String uid);
}
