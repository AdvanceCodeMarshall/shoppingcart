package com.shoppingcart.jwtconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.shoppingcart.beans.request.LoginRequest;
import com.shoppingcart.model.User;
import com.shoppingcart.service.userServices.UserService;

@Configuration
public class AuthManager {

	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(AuthManager.class);

	/**
	 * 
	 * @param authenticationToken
	 * @param loginRequest
	 * @return
	 */
	public Authentication authenticate(UsernamePasswordAuthenticationToken authenticationToken,
			LoginRequest loginRequest) throws AuthenticationException {
		String mobile = authenticationToken.getPrincipal() + "";
		String password = authenticationToken.getCredentials() + "";
		User user;
		try {
			logger.info("User is going to validate(Authmanager)" + mobile);
			if (userService == null) {
				logger.info("User found the error");
				throw new BadCredentialsException("1001");
			}
			user = userService.findByMobile(mobile);

			if (user == null) {
				throw new BadCredentialsException("User Not found!!");
			}
			logger.info("from authentication " + password + " from db " + user.getPassword());
			if (!this.passwordmatch(password, user.getPassword())) {
				logger.info("Password is wrong for user .." + user.getEmail() + "-- " + user.getMobile());
				throw new BadCredentialsException("Mobile or password is wrong.");
			}

			return new UsernamePasswordAuthenticationToken(new UserPrincipal(user.getId(), mobile, password), password);
		} catch (Exception e) {
			logger.info("Error", e);
			throw new BadCredentialsException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param rawPassword
	 * @param from_db_encoded
	 * @return
	 */
	private boolean passwordmatch(String rawPassword, String from_db_encoded) {
		return rawPassword.equals(from_db_encoded);
	}

}
