package tqs.group4.bestofbooks.controller;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.group4.bestofbooks.dto.UserDto;
import tqs.group4.bestofbooks.exception.LoginFailedException;
import tqs.group4.bestofbooks.exception.LoginRequiredException;
import tqs.group4.bestofbooks.exception.RegistrationFailedException;
import tqs.group4.bestofbooks.exception.RepeatedPublisherNameException;
import tqs.group4.bestofbooks.exception.RepeatedUsernameException;
import tqs.group4.bestofbooks.exception.UserNotFoundException;
import tqs.group4.bestofbooks.service.LoginServices;

@CrossOrigin
@RestController
@RequestMapping("/api/session")
public class SessionController {

	@Autowired
	private LoginServices loginService;
	
	private static String badAuthHeaderMessage = "Bad authorization header.";
	
	@GetMapping("/login")
	public ResponseEntity<UserDto> login(HttpServletRequest request) throws LoginFailedException, UserNotFoundException {
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		String[] headerParts = auth.trim().split(" ");

		if (headerParts.length != 2) {
			throw new LoginFailedException(badAuthHeaderMessage);
		} else if (!headerParts[0].equals("Basic")) {
			throw new LoginFailedException("Unsupported authorization header type.");
		}

		String[] decodedTokenParts = new String(Base64.getDecoder().decode(headerParts[1])).split(":");

		if (decodedTokenParts.length != 2) {
			throw new LoginFailedException(badAuthHeaderMessage);
		}
		
		String username = decodedTokenParts[0];
		String password = decodedTokenParts[1];

		UserDto user = loginService.loginUser(username, password);
		
		loginService.setSessionUsername(request, user.getUsername());

		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@PutMapping("/login")
	public ResponseEntity<UserDto> register(HttpServletRequest request, @Valid @RequestBody UserDto newUser) throws RegistrationFailedException, RepeatedUsernameException, RepeatedPublisherNameException {
		
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		String[] headerParts = auth.trim().split(" ");

		if (headerParts.length != 2) {
			throw new RegistrationFailedException(badAuthHeaderMessage);
		} else if (!headerParts[0].equals("Basic")) {
			throw new RegistrationFailedException("Unsupported authorization header type.");
		}

		String[] decodedTokenParts = new String(Base64.getDecoder().decode(headerParts[1])).split(":");

		if (decodedTokenParts.length != 2) {
			throw new RegistrationFailedException(badAuthHeaderMessage);
		}
		
		String username = decodedTokenParts[0];
		String password = decodedTokenParts[1];
		
		if (!username.equals(newUser.getUsername())) {
			throw new RegistrationFailedException("Inconsistent username.");
		}
		
		UserDto user = loginService.registerUser(newUser, password);
		
		loginService.setSessionUsername(request, username);
		
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
	
	@GetMapping("/user-info")
	public ResponseEntity<UserDto> getUserInfo(HttpServletRequest request) throws UserNotFoundException, LoginRequiredException{
		String user = loginService.getSessionUsername(request);
		
		if (user == null) {
			throw new LoginRequiredException("Login Required for this request.");
		}
		
		return new ResponseEntity<>(loginService.getUserDtoByUsername(user), HttpStatus.OK);
	}
	
	
}
