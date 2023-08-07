package com.example.demo.responsive;

import com.example.demo.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog,Long> {

    @Query("SELECT b.title FROM Blog b where 1=1")
    List<String> findAllName();

    Optional<Blog> getPostById(Long id);

}
