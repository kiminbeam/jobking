package com.example.jobking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "manager")
public class Manager extends BaseEntity {

	@Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "pw", nullable = false)
    private int pw;

    @Column(name = "role", length = 1, nullable = false)
    private String role;
}
