package com.twitter.server.models;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	private Integer userId;
	private String firstName;
	private String lastName;
	@Column(unique=true)
	private String userName;
	@JsonIgnore
	private String password;
	
	@Column(unique=true)
	private String email;
	private String place;
	private Date dob;
	private String phone;
	private String joinedDate;
	private String following;
	private String followers;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="user_role_juncton",
			joinColumns= {@JoinColumn(name="user_id")},
			inverseJoinColumns= {@JoinColumn(name="role_id")}
			)
	private Set<Role> authorities;
	
	private Boolean enabled;
	@Column(nullable = true)
	@JsonIgnore
	private Long verification;
	
	public User() {
		this.authorities=new HashSet<>();
		this.enabled=false;
	}
}
