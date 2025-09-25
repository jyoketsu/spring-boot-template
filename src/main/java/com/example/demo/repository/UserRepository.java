package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByUsername(String username);

	Optional<User> findByUsername(String username);

	Optional<User> findByWechatOpenId(String wechatOpenId);

	long count();
}
