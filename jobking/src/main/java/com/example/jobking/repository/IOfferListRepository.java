package com.example.jobking.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.OfferList;

public interface IOfferListRepository extends JpaRepository<OfferList, Long> {
	//면접 완료된 사람만 받는 리스트
	@Query(value="select * from offer_list where uid= uid  and status=2", nativeQuery = true)
	public List<OfferList> findByUid(@Param("uid") String uid);

	//해당 아이디로 제안 들어온거 다 보여주는 리스트
	@Query(value="select * from offer_list where uid= :uid", nativeQuery=true)
	List<OfferList> findAllByUid(@Param("uid") String uid);
	
	//해당 유저아이디로 들어온 제안 중 마지막 로그인 이후로 들어온 제안만 받는 리스트
	@Query(value="select * from offer_list where uid= :uid and regdate > :regdate", nativeQuery=true)
	List<OfferList> findOfferByUidAfterLastLogin(@Param("uid") String uid, @Param("regdate") Date regdate);
	
	//해당 기업아이디로 보낸 제안 중 마지막 로그인 이후로 accept속성의 status가 변한 제안만 받는 리스트
	@Query(value="select * from offer_list where cid= :cid and moddate > :regdate", nativeQuery=true)
	List<OfferList> findReactedOfferByCidAfterLastLogin(@Param("cid") String cid, @Param("regdate") Date regdate);

	List<OfferList> findByCompanyCid(String cid);
	
	Optional<OfferList> findByUserUidAndCompanyCid(String uid, String cid);
	
}
