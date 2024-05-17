package com.example.jobking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "company_review")
public class CompanyReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crno", nullable = false)
    private Long crno;

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
