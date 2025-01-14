package com.example.demo.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

/**
 * https://github.com/jwtk/jjwt#jws-create-key
 */
@Component
public class JwtUtils {
	// HS256
	SecretKey key = Jwts.SIG.HS256.key().build();
	// 24小时
	private final long jwtExpirationMs = 86400000;

	/**
	 * Generates a JWT token for the given user details.
	 *
	 * @param userDetails the user details containing the username
	 * @return a signed JWT token as a String
	 */
	public String generateToken(UserDetails userDetails) {
		String username = userDetails.getUsername();
		Date expirationDate = new Date((new Date()).getTime() + jwtExpirationMs);
		String jws = Jwts.builder().subject(username).expiration(expirationDate).signWith(key).compact();
		return jws;
	}

	/**
	 * Returns the username from the given JWT token.
	 * 
	 * @param token the JWT token
	 * @return the username
	 */
	public String getUsernameFromToken(String token) {
		return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
	}

	/**
	 * Validate a JWT token. This method parses the given JWT token, using the
	 * previously set secret key, and verifies its signature.
	 * 
	 * @param token the JWT token to be validated
	 * @return true if the token is valid, false otherwise
	 */
	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}
}
