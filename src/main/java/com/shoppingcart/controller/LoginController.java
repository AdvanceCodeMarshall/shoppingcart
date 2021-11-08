package com.shoppingcart.controller;

import java.util.HashMap;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.beans.request.LoginRequest;
import com.shoppingcart.beans.response.ApiResponse;
import com.shoppingcart.jwtconfig.AuthManager;
import com.shoppingcart.jwtconfig.JwtTokenProvider;
import com.shoppingcart.jwtconfig.UserPrincipal;
import com.shoppingcart.model.User;
import com.shoppingcart.service.userServices.UserService;

@RestController
@RequestMapping("/api/login")
public class LoginController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	AuthManager authManager;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@GetMapping("/status")
	public ResponseEntity<?> serverStatus() {
		System.out.println("inside user");
		return new ResponseEntity<>(new ApiResponse("Server is running.", ""), HttpStatus.OK);
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> userLogin(@RequestBody LoginRequest loginRequest) {

		try {
			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getMobile(), loginRequest.getPassword()),
					loginRequest);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtTokenProvider.generateToken(authentication);
			JSONObject obj = this.getUserResponse(token);
			if (obj == null) {
				throw new Exception("Error while generating response");
			}
			return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);
		} catch (Exception e) {
			logger.info("Error in authenticateUser ", e);
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}
	}

	private JSONObject getUserResponse(String token) {
		try {
			User user = userService.getUserDetailById(_getUserId());
			HashMap<String, String> response = new HashMap<String, String>();
			response.put("user_id", "" + _getUserId());
			response.put("email", user.getEmail());
			response.put("name", user.getName());
			response.put("mobile", user.getMobile());

			JSONObject obj = new JSONObject();

			obj.put("user_profile_details", response);
			obj.put("token", token);

			return obj;
		} catch (Exception e) {
			logger.info("Error in getUserResponse ", e);
		}
		return null;
	}

	private long _getUserId() {
		logger.info("user id vaildating. " + SecurityContextHolder.getContext().getAuthentication());
		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		logger.info("(LoginController)user id is " + userPrincipal.getId());
		return userPrincipal.getId();
	}

}
