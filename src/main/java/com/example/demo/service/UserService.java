package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.Dto.ChangePasswordRequest;
import com.example.demo.Dto.CreateCommentPostRequest;
import com.example.demo.Dto.CreatePostRequest;
import com.example.demo.Dto.UserDto;
//import com.example.demo.entity.Inter;
import com.example.demo.entity.Blog;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Role;
//import com.example.demo.entity.Menter;
import com.example.demo.entity.User;
//import com.example.demo.responsive.InterResponsive;

public interface UserService {


	User updateProfile(User inter);

	User changePassword(String newPassword);

	List<User> findInterAllById(List<Long> ids);

	User findInterById(Long id);

	User findMenterById(Long id);

	List<UserDto> findAll();

	void deleteById(Long id);

	void checkEmail(String email);

	User saveUser(User entity);

	void addLang(String email, String langName);

	List<Role> getByEmail(User user);

//	void addInter(String email);
//
//	void addMenter(String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	Role saveRole(Role role);

	Optional<User> findByUsername(String username);

	User findById(Long id);

	void changePassword(User user, ChangePasswordRequest changePasswordRequest);

	List<UserDto> getListView();

	List<UserDto> findAllMentor();

	List<UserDto> findByKeyword(String keyword);

	Blog createPost(CreatePostRequest createPostRequest, User user);

	Comment createCommentPost(CreateCommentPostRequest createCommentPostRequest, long userId);
}
