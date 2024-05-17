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
@Table(name = "user_reply")
public class UserReply extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "replyno", nullable = false)
	private Long replyno;

	@ManyToOne
	@JoinColumn(name = "uid", referencedColumnName = "uid", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "ubno", referencedColumnName = "ubno", nullable = false)
	private UserBoard userBoard;

	@Column(name = "content", columnDefinition = "TEXT")
	private String content;

	@Column(name = "writer", length = 30)
	private String writer;
}
