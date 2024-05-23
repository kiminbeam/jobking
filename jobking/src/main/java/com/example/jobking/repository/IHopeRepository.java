package com.example.jobking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.Hope;
import com.example.jobking.entity.Resume;
import com.example.jobking.entity.User;


public interface IHopeRepository extends JpaRepository<Hope, Long> {
	
	@Query(value="select * from hope where uid= :uid and rno= :rno " , nativeQuery=true)
	public Hope findByUidAndRno(@Param("uid") String uid, @Param("rno") Long rno);
	
	// 유저와 이력서를 기준으로 희망 조건 조회
	Hope findByUserAndResume(User user, Resume resume);
    
    List<Hope> findByResumeRno(Long rno);
	
    @Query(value="delete from hope where hno = :hno" , nativeQuery=true)
	public void deleteHopeByHno(@Param("hno") Long hno);
 
    void deleteByResumeRno(Long rno);
}
