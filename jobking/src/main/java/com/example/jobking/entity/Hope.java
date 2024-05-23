package com.example.jobking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "hope")
public class Hope extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hno", nullable = false)
	private Long hno;

	@ManyToOne
	@JoinColumn(name = "rno", referencedColumnName = "rno", nullable = false)
	private Resume resume;

	@ManyToOne
	@JoinColumn(name = "uid", referencedColumnName = "uid", nullable = false)
	private User user;

	@Column(name = "region1", nullable = false)
	private String region1;

	@Column(name = "region2")
	private String region2;

	@Column(name = "region3")
	private String region3;

	@Column(name = "sectors", nullable = false)
	private String sectors;

	@Column(name = "job", nullable = false)
	private String job;
	
	public void update(Hope newHope) {
        this.region1 = newHope.getRegion1();
        this.region2 = newHope.getRegion2();
        this.region3 = newHope.getRegion3();
        this.sectors = newHope.getSectors();
        this.job = newHope.getJob();
        // 필요에 따라 다른 필드도 업데이트
    }
	
}
