package com.example.demo.responsive;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Inter;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Mentor;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long>{
	
//	@Query("SELECT u.email FROM User u join Menter m on u.id = m.users.id where u.email = :email")

	@Query("SELECT r FROM Mentor r join User u on u.id = r.users.id where u.username = :username")
	Optional<Mentor> findByUsername(String username);

	@Query("SELECT m.id FROM Mentor m  join  User u on u.id = m.users.id where u.username = :username")
	Optional<Long> checkIDMentor(String username);



//	@Query("SELECT e FROM Menter e WHERE e.email = ?1")
//	Menter findByEmail(String email);


	
}
