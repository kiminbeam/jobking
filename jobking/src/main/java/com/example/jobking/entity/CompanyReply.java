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
@Table(name = "company_reply")
public class CompanyReply extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "replyno", nullable = false)
    private Long replyno;

    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "cid", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "cbno", referencedColumnName = "cbno", nullable = false)
    private CompanyBoard companyBoard;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "writer", length = 30)
    private String writer;	
}
