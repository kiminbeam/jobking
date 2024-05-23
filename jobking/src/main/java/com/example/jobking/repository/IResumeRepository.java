package com.example.jobking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.Resume;
import com.example.jobking.entity.User;

public interface IResumeRepository extends JpaRepository<Resume, Long> {

	@Query(value="select * from resume where uid= :uid " , nativeQuery=true)
	public List<Resume> findByUid(@Param("uid") String uid);
	
	@Query(value="select * from resume where uid= :uid and def=1 " , nativeQuery=true)
	public Resume findDefByUid(@Param("uid") String uid);
	
	@Query(value="select rno from resume where uid = :uid order by regdate DESC limit 1", nativeQuery=true)
	public Long findlatestRno(@Param("uid") String uid);
	
	List<Resume> findByUser_uid(String uid);
	
	// 이력서가 특정 사용자에 의해 존재하는지 여부를 확인하는 메서드 추가
    boolean existsByUser_Uid(String uid);
    
    @Query("SELECT r FROM Resume r WHERE r.user = :user AND r.def = :def AND r.disclo = :disclo")
    Resume findByUserAndDefAndDisclo(@Param("user") User user, @Param("def") String def, @Param("disclo") String disclo);

	long countByDef(String def); 
	
	@Query("SELECT r FROM Resume r JOIN FETCH r.user WHERE r.user.uid = :uid AND r.rno = :rno")
	Optional<Resume> findResumeWithUserById(@Param("uid") String uid, @Param("rno") Long rno);
	
	@Query(value="delete from resume where rno = :rno" , nativeQuery=true)
	public void deleteResumeByRno(@Param("rno") Long rno);
}
