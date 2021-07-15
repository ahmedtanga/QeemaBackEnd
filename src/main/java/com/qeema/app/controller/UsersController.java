package com.qeema.app.controller;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qeema.app.dto.UsersDto;
import com.qeema.app.entity.UsersEntity;
import com.qeema.app.repository.UsersRepository;
import com.qeema.app.service.UsersService;
import com.qeema.app.service.WebSocketService;

@CrossOrigin
@RestController
@RequestMapping(path = "/user", produces = "application/json")
public class UsersController {

	@Autowired
	UsersService usersService;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	WebSocketService webSocketService;
	
    HashSet<String> loggedInUser = new HashSet<String>();
	
	
	@PostMapping(path = "/register")
	public ResponseEntity<?> register(@RequestBody UsersDto usersDto) {
		try {
			UsersEntity userEntity = new UsersEntity();
			userEntity = usersRepository.getUserByEmail(usersDto.getEmail());
			if (userEntity != null) {
				return new ResponseEntity<>("Email already Exists!", HttpStatus.BAD_REQUEST);
			}
			// this.template.convertAndSend("/topic/getUsers",
			// userService.getTotalNumberOfUsers());
			
			UsersDto res=usersService.register(usersDto);
			this.notifyFrontend();
			return new ResponseEntity<>(res, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping(path = "/login")
	public ResponseEntity<?> login(@RequestBody UsersDto usersDto) {
		try {
			
			loggedInUser.add(usersDto.getEmail());
			notifyFrontend();
			
			return usersService.login(usersDto);
		} catch (Exception e) {
			return new ResponseEntity<>("invalid Username or Password!", HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping(path = "/logout")
	public ResponseEntity<?> logout(@RequestParam(value = "email") String email) {
		try {
			loggedInUser.remove(email);
			notifyFrontend();
			return usersService.logout(email);
		} catch (Exception e) {
			return new ResponseEntity<>("internal Serval Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(path = "getTotalNumberOfUsers", produces = "application/json")
	public ResponseEntity<?> getTotalNumberOfUsers() {
		try {

			usersService.getTotalNumberOfUsers();
			// this.template.convertAndSend("/topic/getUsers",
			// userService.getTotalNumberOfUsers());

		} catch (Exception e) {
			return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(usersService.getTotalNumberOfUsers(), HttpStatus.OK);

	}
	
	
	@GetMapping(path = "getAllUsers", produces = "application/json")
	public ResponseEntity<?> getAllUsers() {
		
		return new ResponseEntity<>(usersService.getAllUsers(), HttpStatus.OK);

	}
	
	
	private void notifyFrontend() {
        webSocketService.sendMessage("countloggedIN",loggedInUser.size()+"");
        webSocketService.sendMessage("countUsers",usersService.getTotalNumberOfUsers().getTotalNoOfUsers()+"");

    }
}
