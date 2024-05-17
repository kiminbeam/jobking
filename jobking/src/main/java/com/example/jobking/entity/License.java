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
@Table(name = "license")
public class License extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lno", nullable = false)
	private Long lno;

	@ManyToOne
	@JoinColumn(name = "rno", referencedColumnName = "rno", nullable = false)
	private Resume resume;

	@ManyToOne
	@JoinColumn(name = "uid", referencedColumnName = "uid", nullable = false)
	private User user;

	@Column(name = "type")
	private String type;

	@Column(name = "lname")
	private String lname;

	@Column(name = "org")
	private String org;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "getDate")
	private Date getDate;
}
