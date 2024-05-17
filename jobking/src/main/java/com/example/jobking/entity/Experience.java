package com.example.jobking.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "startDay")
    private Date startDay;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "endDay")
    private Date endDay;

    @Column(name = "org")
    private String org;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
}
