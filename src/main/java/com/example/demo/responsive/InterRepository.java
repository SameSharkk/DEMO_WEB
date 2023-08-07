package com.example.demo.responsive;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Inter;
import com.example.demo.entity.User;

@Repository
public interface InterRepository extends JpaRepository<Inter, Long>{
	
	@Query("SELECT u.username FROM User u join Inter r on u.id = r.users.id where u.username = :username")
	Optional<String> checkInterUserName(String username);

	@Query("SELECT r FROM Inter r join User u on u.id = r.users.id where u.username = :username")
	Optional<Inter> findByUsername(String username);

	
}
