package com.qeema.app.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.qeema.app.dto.TotalOfUsersDto;
import com.qeema.app.dto.UsersDto;
import com.qeema.app.entity.*;

@Service
public interface UsersService {

	public UsersDto register(UsersDto usersDto);

	public ResponseEntity<?> login(UsersDto usersDto);

	public ResponseEntity<?> logout(String email);

	public TotalOfUsersDto getTotalNumberOfUsers();
	
	public ResponseEntity<?> getAllUsers();


}
