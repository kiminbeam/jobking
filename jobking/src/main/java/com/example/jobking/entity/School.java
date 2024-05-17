package com.example.jobking.entity;

import java.math.BigDecimal;
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
@Table(name = "school")
public class School extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sno", nullable = false)
	private Long sno;

	@ManyToOne
	@JoinColumn(name = "rno", referencedColumnName = "rno", nullable = false)
	private Resume resume;

	@ManyToOne
	@JoinColumn(name = "uid", referencedColumnName = "uid", nullable = false)
	private User user;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "accDate", nullable = false)
	private Date accDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "gradDate", nullable = false)
	private Date gradDate;

	@Column(name = "eduName", nullable = false)
	private String eduName;

	@Column(name = "major")
	private String major;

	@Column(name = "score", precision = 3, scale = 2)
	private BigDecimal score;

	@Column(name = "status", nullable = false)
	private String status;
}
