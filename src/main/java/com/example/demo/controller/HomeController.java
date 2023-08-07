package com.example.demo.controller;

import com.example.demo.Dto.ChangePasswordRequest;
import com.example.demo.Dto.CreateCommentPostRequest;
import com.example.demo.Dto.RequestUserChange;
import com.example.demo.Dto.UserDto;
import com.example.demo.Dto.mapper.UserMapper;
import com.example.demo.entity.*;
import com.example.demo.responsive.*;
import com.example.demo.service.UserService;
import com.example.demo.service.serviceImpl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.boot.model.internal.XMLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.auth.request.SignInForm;
import com.example.demo.auth.request.SignUpForm;
import com.example.demo.auth.response.tokenResponse;
import com.example.demo.auth.response.ResponseMessage;
import com.example.demo.security.jwt.AuthenticationService;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class HomeController {

	@Autowired
	AuthenticationService authSer;

	@Autowired
	UserServiceImpl userService;

	@Autowired
	RoleRepository roleService;

	@Autowired
	InterRepository interRepo;

	@Autowired
	MentorRepository mentorRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	BlogRepository blogRepo;

	private final RoleCustomRepo roleCusRepo;
	
	@GetMapping(value= {"", "/home"})
	public ResponseEntity<?> goHome() {
		//Lấy 5 mentor có lượt xem nhiều
		List<UserDto> viewMentors = userService.getListView();
		return ResponseEntity.ok(viewMentors);
	}


	@GetMapping("/loginPage")
	public String getInLogin( Model model){
		SignUpForm signUpForm = new SignUpForm();
		SignInForm signInForm = new SignInForm();
		model.addAttribute("signIn", signInForm);
		model.addAttribute("signUp", signUpForm);
		return "index";
	}

	@PostMapping("/signup")
	public ResponseEntity<?> register(@ModelAttribute("signUp") SignUpForm signUpForm){
		System.out.println(signUpForm.getName());
		System.out.println(signUpForm.getUsername());
		System.out.println(signUpForm.getEmail());
		System.out.println(signUpForm.getPassword());
		System.out.println(signUpForm);
		if(userService.existsByUsername(signUpForm.getUsername())){
			return new ResponseEntity<>(new ResponseMessage("The username is existed"), HttpStatus.OK);
		}
		if(userService.existsByEmail(signUpForm.getEmail())){
			return new ResponseEntity<>(new ResponseMessage("The email is existed"), HttpStatus.OK);
		}
		User users = new User(signUpForm.getName(), signUpForm.getUsername(), signUpForm.getEmail(), passwordEncoder.encode(signUpForm.getPassword()));
		Set<String> strRoles = signUpForm.getRoles();
		Set<Role> roles = new HashSet<>();
		strRoles.forEach(role ->{
			switch (role){
				case "admin":
					Role adminRole = roleService.findByName("ADMIN").orElseThrow( ()-> new RuntimeException("Role not found"));
					roles.add(adminRole);
					break;
				case "mentor":
					Role mRole = roleService.findByName("MENTOR").orElseThrow( ()-> new RuntimeException("Role not found"));
					roles.add(mRole);
					Mentor m = new Mentor();
					mentorRepo.save(m);
					m.setUsers(users);
					break;
				default:
					Role inRole = roleService.findByName("INTER").orElseThrow( ()-> new RuntimeException("Role not found"));
					roles.add(inRole);
					Inter i = new Inter();
					interRepo.save(i);
					i.setUsers(users);
			}
		});
		users.setRoles(roles);
		userService.saveUser(users);
		return new ResponseEntity<>(new ResponseMessage("Create success!"), HttpStatus.OK);
	}
	
	@PostMapping(value = {"/signin"} , produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<tokenResponse> login(@ModelAttribute("signInForm") SignInForm signInForm, HttpServletRequest request) {
		return ResponseEntity.ok(authSer.authenticate(signInForm));
	}
	
	
	@GetMapping("/demo")
	public String hello() {
		return "/styles";
	}


	@GetMapping("/account/{username}")
	@ResponseBody
	public UserDto getCurrentUser(@PathVariable(name = "username") String currentUserName) {
		UserDto userDto = UserMapper.toUserDto(userService.findByUsername(currentUserName).get());
		List<String> role = new ArrayList<>();
		for(Role roles: roleCusRepo.getRole(userService.findByUsername(currentUserName).get())){
			role.add(roles.getName());
		}
		userDto.setRole(role);

		return userDto;
	}


	@PutMapping("/changeProfile/{username}")
	public ResponseEntity<?> getChange(@PathVariable(name = "username") String currentUserName, @RequestBody RequestUserChange userUpLoad){
		User u = userService.findByUsername(currentUserName).get();
		u.setName(userUpLoad.getName());
		u.setEmail(userUpLoad.getEmail());

		userService.saveUser(u);
		List<String> role = new ArrayList<>();
		for(Role roles: roleCusRepo.getRole(userService.findByUsername(currentUserName).get())){
			role.add(roles.getName());
		}
		UserDto user = UserMapper.toUserDto(u);
		user.setRole(role);
		return ResponseEntity.ok(user);
	}

	@GetMapping("/getMentor/{id}")
	public ResponseEntity<?> getMentorDetail(@PathVariable Long id){
		User user = userService.findById(id);
		if(mentorRepo.findByUsername(user.getUsername()).isEmpty()){
			throw new IllegalStateException("mentor nay ko ton tai");
		}
		UserDto userDto = UserMapper.toUserDto(user);

		return ResponseEntity.ok(userDto);
	}

	@GetMapping("/getListMentor")
	public ResponseEntity<?> getListMentor(){
		List<UserDto> users = userService.findAllMentor();

		return ResponseEntity.ok(users);
	}


	@PostMapping("/change-password/{username}")
	public ResponseEntity<Object> changePassword (@PathVariable(name = "username") String username, @RequestBody ChangePasswordRequest passwordReq) {
		User user = userService.findByUsername(username).get();
		userService.changePassword(user, passwordReq);
		return ResponseEntity.ok("Đổi mật khẩu thành công");
	}

	@GetMapping("/api/tim-kiem")
	public ResponseEntity<Object>  searchMentor(@RequestParam(name = "keyword" ,required = false) String keyword) {
		List<UserDto> user = userService.findByKeyword(keyword);
		return ResponseEntity.ok(user);
	}


	@GetMapping("/tin-tuc")
	public ResponseEntity<Object> getBlog() {
		List<String> posts = blogRepo.findAllName();
		return ResponseEntity.ok(posts);
	}

	@GetMapping("/tin-tuc/{id}")
	public ResponseEntity<Object> getBlogDetail(@PathVariable Long id){
		if(blogRepo.getPostById(id).isEmpty()){
			throw new IllegalStateException("Ko tim thay id blog");
		}
		Blog post = blogRepo.getPostById(id).get();
		return ResponseEntity.ok(post);
	}

	@PostMapping("/api/tin-tuc/comment")
	public ResponseEntity<Object> createComment(@Valid @RequestBody CreateCommentPostRequest createCommentPostRequest, @RequestParam(name = "username") String username) {
		User user = userService.findByUsername(username).get();
		Comment comment = userService.createCommentPost(createCommentPostRequest, user.getId());
		return ResponseEntity.ok(comment);
	}




}
