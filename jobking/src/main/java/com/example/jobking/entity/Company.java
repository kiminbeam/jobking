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
@Table(name = "company")
public class Company extends BaseEntity {

		@Id
	    @Column(name = "cid", nullable = false, unique = true)
	    private String cid;

	    @Column(name = "logo")
	    private String logo;

	    @Column(name = "cname", nullable = false)
	    private String cname;

	    @Column(name = "cpw", nullable = false)
	    private String cpw;

	    @Column(name = "ceo", nullable = false)
	    private String ceo;

	    @Column(name = "cnum", nullable = false)
	    private String cnum;

	    @Column(name = "caddr", nullable = false)
	    private String caddr;

	    @Column(name = "sector", nullable = false)
	    private String sector;

	    @Column(name = "employees", nullable = false)
	    private int employees;

	    @Column(name = "url")
	    private String url;

	    @Column(name = "size", nullable = false)
	    private String size;

	    @Column(name = "major", nullable = false)
	    private String major;

	    @Column(name = "yrSales", nullable = false)
	    private String yrSales;
	
}
