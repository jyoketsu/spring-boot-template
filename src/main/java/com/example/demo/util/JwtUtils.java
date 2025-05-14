package com.example.demo.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

/**
 * https://github.com/jwtk/jjwt#jws-create-key
 */
@Component
public class JwtUtils {
	// HS256
	SecretKey key = Jwts.SIG.HS256.key().build();

	// 2小时
	private Long accessTokenExpiration = 7200000L;
	// 7天
	private Long refreshTokenExpiration = 604800000L;

	private static final String TOKEN_BLACKLIST_PREFIX = "token:blacklist:";

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public String generateAccessToken(UserDetails userDetails) {
		return generateToken(userDetails, accessTokenExpiration);
	}

	public String generateRefreshToken(UserDetails userDetails) {
		return generateToken(userDetails, refreshTokenExpiration);
	}

	private String generateToken(UserDetails userDetails, long expiration) {
		String username = userDetails.getUsername();
		Date expirationDate = new Date(System.currentTimeMillis() + expiration);
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

	public boolean isTokenBlacklisted(String token) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(TOKEN_BLACKLIST_PREFIX + token));
	}

	public void addToBlacklist(String token) {
		// 获取token的过期时间
		Date expiration = getExpirationDateFromToken(token);
		long ttl = expiration.getTime() - System.currentTimeMillis();
		if (ttl > 0) {
			redisTemplate.opsForValue().set(
					TOKEN_BLACKLIST_PREFIX + token,
					"blacklisted",
					ttl,
					TimeUnit.MILLISECONDS);
		}
	}

	public Date getExpirationDateFromToken(String token) {
		Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
		return claims.getExpiration();
	}

	/**
	 * 判断是否是refresh token
	 * 
	 * @param token
	 * @return
	 */
	public boolean isRefreshToken(String token) {
		try {
			Claims claims = Jwts.parser()
					.verifyWith(key)
					.build()
					.parseSignedClaims(token)
					.getPayload();

			// 通过过期时间判断是否是refresh token（根据业务逻辑调整）
			long expiration = claims.getExpiration().getTime();
			long current = System.currentTimeMillis();
			return (expiration - current) > accessTokenExpiration;
		} catch (JwtException e) {
			return false;
		}
	}
}
