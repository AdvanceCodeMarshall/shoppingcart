package com.shoppingcart.service.userServices;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.shoppingcart.model.User;

@Service
public interface UserService {
	
	/**
	 * 
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	User findByMobile(String mobile) throws Exception;
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	User getUserDetailById(Long userId) throws Exception;
	
	/**
	 * 
	 * @param signUpRequest
	 * @return
	 * @throws Exception
	 */
	User signUpUser(HashMap<String, String> signUpRequest) throws Exception;
	
}
