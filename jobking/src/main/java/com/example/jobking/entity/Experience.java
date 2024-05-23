package com.example.jobking.entity;

import java.time.LocalDate;

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
@Table(name = "experience")
public class Experience extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eno", nullable = false)
    private Long eno;

	@ManyToOne
    @JoinColumn(name = "rno", referencedColumnName = "rno", nullable = false)
    private Resume resume;

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid", nullable = false)
    private User user;

    @Column(name = "startDay")
    private LocalDate startDay;
    
    @Column(name = "endDay")
    private LocalDate endDay;

    @Column(name = "org")
    private String org;

    @Column(name = "econtent", columnDefinition = "TEXT")
    private String econtent;
    
    public void update(Experience newExperience) {
        this.startDay = newExperience.getStartDay();
        this.endDay = newExperience.getEndDay();
        this.org = newExperience.getOrg();
        this.econtent = newExperience.getEcontent();
        // 필요에 따라 다른 필드도 업데이트
    }
    
}
