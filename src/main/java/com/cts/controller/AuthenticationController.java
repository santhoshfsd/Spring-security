package com.cts.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.cts.model.User;
import com.cts.model.UserDto;
import com.cts.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.cts.config.JwtTokenUtil;
import com.cts.model.LoginRequest;
import com.cts.model.TokenResponse;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest)
			throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);

		User user = userRepository.findByUsername(authenticationRequest.getUsername());
		TokenResponse response = new TokenResponse();
		response.setToken(token);
		Map<String, String> userDetailsResponse = new HashMap<>();
		userDetailsResponse.put("username",user.getUsername());
		userDetailsResponse.put("email",user.getEmail());
		response.setUserDetails(userDetailsResponse);
		return ResponseEntity.ok(response);
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		catch (Exception e) {
			throw new Exception("Unknown exception", e);
		}
	}

	@PostMapping("/register")
	public ResponseEntity registerUser(@RequestBody UserDto userDto) {
		User existingUser = userRepository.findByUsername(userDto.getUsername());
		String message = null;
		Integer status = null;
		if(existingUser == null) {
			User user = new User();
			BeanUtils.copyProperties(userDto, user);
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			User persistedUser = userRepository.save(user);
			message = "User Created Successfully";
			status = 201;
		} else {
			message = "User Already registered";
			status = 500;
		}
		Map response = new HashMap<>();
		response.put("message", message);
		response.put("status", status);
			return ResponseEntity.status(status).body(response);
	}
}
