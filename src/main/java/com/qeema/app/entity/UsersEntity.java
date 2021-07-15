package com.qeema.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users ")
public class UsersEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = " REC_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "USER_NAME")
	private String username;

	@Column(name = "EMAIL", unique = true)
	private String email;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "isLogged")
	private Boolean isLogged;
	
	@Column(name = "token")
	private String token;

	
	
	
	public UsersEntity() {
	}

	public UsersEntity(Long id, String username, String email, String password, Boolean isLogged) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.isLogged = isLogged;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsLogged() {
		return isLogged;
	}

	public void setIsLogged(Boolean isLogged) {
		this.isLogged = isLogged;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	

}
