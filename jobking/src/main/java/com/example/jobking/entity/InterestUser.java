package com.example.jobking.entity;

import java.util.Optional;

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
@Table(name = "interest_user")
public class InterestUser extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "interuno", nullable = false)
	private Long interuno;

	@ManyToOne
	@JoinColumn(name = "uid", referencedColumnName = "uid", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "cid", referencedColumnName = "cid", nullable = false)
	private Company company;
	
	
	@ManyToOne
    @JoinColumn(name = "rno", referencedColumnName = "rno", nullable = false)
    private Resume resume;
}
