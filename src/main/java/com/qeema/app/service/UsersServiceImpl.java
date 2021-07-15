package com.qeema.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.qeema.app.dto.LoginResponse;
import com.qeema.app.dto.TotalOfUsersDto;
import com.qeema.app.dto.UsersDto;
import com.qeema.app.entity.UsersEntity;
import com.qeema.app.repository.UsersRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	UsersRepository usersRepository;

	private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

	@Override
	public UsersDto register(UsersDto usersDto) {
		UsersEntity users = new UsersEntity();
		users.setEmail(usersDto.getEmail());
		users.setUsername(usersDto.getUsername());
		users.setPassword(usersDto.getPassword());
		users.setIsLogged(false);
		usersRepository.save(users);
		return usersDto.toDto(users);
	}

	@Override
	public ResponseEntity<?> login(UsersDto usersDto) {
		String token = UUID.randomUUID().toString();
		LoginResponse loginRes = new LoginResponse();
		UsersEntity userEntity = new UsersEntity();
		userEntity = usersRepository.getUserByEmailAndPassword(usersDto.getEmail(), usersDto.getPassword());
		if (userEntity == null) {
			logger.error(" Invaild Username or Password!");
			return new ResponseEntity<>("Invaild Username or Password!", HttpStatus.BAD_REQUEST);
		}
		userEntity.setToken(token);
		userEntity.setIsLogged(true);
		usersRepository.save(userEntity);
		loginRes.setToken(userEntity.getToken());
		loginRes.setUsername(userEntity.getUsername());
		logger.info(loginRes.getUsername() + " Login Successfully");
		return new ResponseEntity<>(loginRes, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> logout(String email) {

		UsersEntity userEntity = new UsersEntity();
		userEntity = usersRepository.getUserByEmail(email);

		if (userEntity == null) {

			return new ResponseEntity<>("email not found", HttpStatus.BAD_REQUEST);

		}

		userEntity.setIsLogged(false);
		userEntity.setToken(null);
		usersRepository.save(userEntity);
		logger.info(userEntity.getEmail() + " Logout Successfully");
		return new ResponseEntity<>("Logout Successfully", HttpStatus.OK);
	}

	@Override
	public TotalOfUsersDto getTotalNumberOfUsers() {
		TotalOfUsersDto totalOfUsersDto = new TotalOfUsersDto();
		totalOfUsersDto.setTotalNoOfUsers(usersRepository.count());
		totalOfUsersDto.setTotalNoOfLoggedUsers(usersRepository.getCountOfUser());
		logger.info("totalNoOfUsers = " + usersRepository.count() + ", TotalNoOfLoggedUsers = "
				+ usersRepository.getCountOfUser());
		return totalOfUsersDto;
	}

	@Override
	public ResponseEntity<?> getAllUsers() {

		List<UsersEntity> users = usersRepository.findAll();

		List response = new ArrayList<>();

		response.add(this.mapEntitylstToDtoLst(users));
		response.add(usersRepository.count());

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	List<UsersDto> mapEntitylstToDtoLst(List<UsersEntity> users) {

		List<UsersDto> dtoLst = new ArrayList<>();

		for (UsersEntity usersEntity : users) {
			UsersDto obj = new UsersDto();
			obj.setEmail(usersEntity.getEmail());
			obj.setUsername(usersEntity.getUsername());
			obj.setIsLogged(usersEntity.getIsLogged());
			dtoLst.add(obj);

		}

		return dtoLst;

	}

}
