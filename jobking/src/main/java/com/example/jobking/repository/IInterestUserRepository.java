package com.example.jobking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.InterestUser;
import com.example.jobking.entity.Resume;
import com.example.jobking.entity.User;

public interface IInterestUserRepository extends JpaRepository<InterestUser, Long> {
	
	boolean existsByUserAndResume(User user, Resume resume); // 스크랩 여부 확인
    void deleteByUserAndResume(User user, Resume resume); // 스크랩 취소
    @Query("SELECT iu.user.uid FROM InterestUser iu WHERE iu.company.cid = :companyId")
    List<String> findScrapUserIdsByCompanyId(@Param("companyId") String companyId);
    
    
    @Query("SELECT iu FROM InterestUser iu WHERE iu.company.cid = :companyId") // InterestUser 객체 전체 조회
    List<InterestUser> findByCompanyCid(@Param("companyId") String companyId);

    Optional<InterestUser> findByUserUidAndCompanyCid(String uid, String cid);
}
