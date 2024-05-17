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
@Table(name = "career")
public class Career extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cno", nullable = false)
    private Long cno;

    @ManyToOne
    @JoinColumn(name = "rno", referencedColumnName = "rno", nullable = false)
    private Resume resume;

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid", nullable = false)
    private User user;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "cName")
    private String cName;

    @Column(name = "rank")
    private String rank;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;

    @Column(name = "position")
    private String position;

    @Column(name = "work")
    private String work;
}
