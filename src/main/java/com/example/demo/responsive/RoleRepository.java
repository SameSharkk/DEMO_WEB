package com.example.demo.responsive;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(String name);
	
//	@Query("select r.name from User u join user_role ur on u.id = ur.user_id join Role r on r.id = ur.role_id where u.email")
//	List<Role> getByEmail(User user);
}
