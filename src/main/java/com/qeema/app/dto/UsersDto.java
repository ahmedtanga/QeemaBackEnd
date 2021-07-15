package com.qeema.app.dto;

import com.qeema.app.entity.UsersEntity;

public class UsersDto {

	private Long id;
	private String username;
	private String email;
	private String password;
	private Boolean isLogged;

	
	
	
	public UsersDto() {
	}

	public UsersDto(String email, String password) {
		this.email = email;
		this.password = password;
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

	public UsersDto toDto(UsersEntity usersEntity) {
		UsersDto user = new UsersDto();
		user.setId(usersEntity.getId());
		user.setEmail(usersEntity.getEmail());
		user.setUsername(usersEntity.getUsername());
		user.setPassword(usersEntity.getPassword());
		user.setIsLogged(usersEntity.getIsLogged());
		return user;
	}

}
