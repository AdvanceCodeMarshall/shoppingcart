package com.shoppingcart.service.userServices.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.model.User;
import com.shoppingcart.repository.UserRepository;
import com.shoppingcart.service.userServices.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findByMobile(String mobile) throws Exception {
		return userRepository.findByMobile(mobile).orElseThrow(() -> new Exception("User not found..."));
	}

	@Override
	public User getUserDetailById(Long userId) throws Exception {	
		return userRepository.findById(userId).orElseThrow(() -> new Exception("User ot found..."));
	}

	@Override
	public User signUpUser(HashMap<String, String> signUpRequest) throws Exception {
		try {
			if (userRepository.findByMobile(signUpRequest.get("mobile")).isPresent()) {
				throw new Exception("User is already registered with Mobile No");
			}
			User user = new User();
			user.setName(signUpRequest.get("name"));
			user.setEmail(signUpRequest.get("email"));
			user.setMobile(signUpRequest.get("mobile"));
			user.setPassword(signUpRequest.get("password"));
			userRepository.save(user);
			return user;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
