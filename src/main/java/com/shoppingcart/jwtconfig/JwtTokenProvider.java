package com.shoppingcart.jwtconfig;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	private static final Logger logger = LogManager.getLogger(JwtTokenProvider.class);

	@Value("${app.jwtSecret}")
	private String jwtSecret;

	@Value("${app.jwtExpirationInMs}")
	private int jwtExpirationInMs;

	/**
	 * This method is for generating token.
	 * 
	 * @param authentication
	 * @return
	 */
	public String generateToken(Authentication authentication) {
		logger.info("Generating JWT Token");
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		Date now = new Date();
		Date expiry = new Date(now.getTime() + jwtExpirationInMs);

		return Jwts.builder().setSubject(Long.toString(userPrincipal.getId())).setIssuedAt(new Date())
				.setExpiration(expiry).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	/**
	 * This method is for getting user id from jwt token.
	 * 
	 * @param token
	 * @return
	 */
	public Long getUserIdByJWT(String token) {
		logger.info("getting user id from JWT");
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(token).getBody();

		return Long.parseLong(claims.getSubject());
	}

	/**
	 * This method is for validating auth toklen.
	 * 
	 * @param authToken
	 * @return
	 */
	public boolean validateToken(String authToken) {

		logger.info("Validating JWT token");
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature");
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException e) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException e) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty.");
		}
		return false;
	}
}
