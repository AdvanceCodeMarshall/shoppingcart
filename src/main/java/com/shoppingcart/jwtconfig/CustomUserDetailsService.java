package com.shoppingcart.jwtconfig;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shoppingcart.model.User;
import com.shoppingcart.service.userServices.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
		try {
			logger.info("loading user by mobile" + mobile);
			User user = userService.findByMobile(mobile);
			return UserPrincipal.create(user);
		} catch (Exception e) {

			throw new UsernameNotFoundException("User not found with Mobile : " + mobile);
		}
	}

	@Transactional
	public UserDetails loadUserById(Long id) {
		try {
			logger.info("loading user by id" + id);
			User user = userService.getUserDetailById(id);
			return UserPrincipal.create(user);
		} catch (Exception e) {
			throw new UsernameNotFoundException("User not found with id : " + id);
		}
	}
}
