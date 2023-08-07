package com.example.demo.responsive;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query("SELECT t FROM User t where t.email = :email")
	Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String username);
	
	@Query("select i.email from User i")
	List<String> getListEmail();

	Optional<User> findById(Long id);
	
    Boolean existsByUsername(String username);
    
    Boolean existsByEmail(String email);

	@Query(nativeQuery = true, value = "SELECT u.* FROM shopsame.user u " +
			"join  shopsame.inter r on  u.id = r.user_id " +
			"join shopsame.mentor m on m.id = r.mentor_id  where m.id =?1")
	List<User> getListInter(Long mentorId);


	@Query (nativeQuery = true, value = "SELECT u.* FROM shopsame.user u join shopsame.mentor m on u.id = m.user_id ORDER BY u.total_view DESC LIMIT ?1")
	List<User> getListView(int limit);

	@Query("SELECT u FROM User u join Mentor m on u.id = m.users.id where 1=1")
	List<User> findAllMentor();

	@Query(nativeQuery = true, value = "SELECT p.* FROM shopsame.user p INNER JOIN shopsame.mentor c ON p.id = c.user_id WHERE p.username LIKE CONCAT('%', ?1 ,'%') LIMIT 3")
	List<User> searchByKeyword(String keyword);
}
