package com.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.store.entities.User;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, String> {

//	User save(UserDto userDto);
	Optional<User> findByEmail(String email);
//	Optional<User> findByEmailandPassword(String email, String password);
	List<User> findByNameContaining(String keyword);
}
