package com.shoppingcart.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.beans.response.ApiResponse;
import com.shoppingcart.model.User;
import com.shoppingcart.service.userServices.UserService;

@RestController
@RequestMapping("api/signup")
public class SignUpController {

	@Autowired
	private UserService userService;

	@PostMapping("/user")
	public ResponseEntity<?> userSignup(@RequestBody HashMap<String, String> signupRequest) {
		try {
			//TODO validation has to add for client request
			User user = userService.signUpUser(signupRequest);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}
	}
}
