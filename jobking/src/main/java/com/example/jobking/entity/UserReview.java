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
@Table(name = "user_review")
public class UserReview extends BaseEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "urno", nullable = false)
    private Long urno;

    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "cid", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "jno", referencedColumnName = "jno", nullable = false)
    private JobAd jobAd;


    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid", nullable = false)
    private User user;


    @Column(name = "q1", nullable = false)
    private String q1;

    @Column(name = "q2", nullable = false)
    private String q2;

    @Column(name = "q3", nullable = false)
    private String q3;

    @Column(name = "feedback")
    private String feedback;
}
