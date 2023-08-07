package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.Dto.CreatePostRequest;
import com.example.demo.Dto.mapper.UserMapper;
import com.example.demo.entity.Blog;
import com.example.demo.responsive.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Dto.UserDto;
import com.example.demo.entity.Role;
//import com.example.demo.entity.Menter;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;



@Controller
@RequiredArgsConstructor
@RequestMapping("/menter")
public class MentorController {
	
	@Autowired
	public UserService userSer;

	@Autowired
	UserRepository userRepo;

	@Autowired
	MentorRepository mentorRepo;

	@Autowired
	InterRepository interRepo;

	@Autowired
	BlogRepository blogRepo;

	private final RoleCustomRepo roleCusRepo;
	
	
	@GetMapping("/{id}")
	@ResponseBody
	public String getToAccount(@PathVariable Long id) {
			return userSer.findMenterById(id).getUsername();
	}


	@GetMapping("/list_inter/{username}")
	public ResponseEntity<?> getInterList(@PathVariable(name ="username") String username ){
		Long mentorID = mentorRepo.checkIDMentor(username).get();
		List<User> inters = userRepo.getListInter(mentorID);
		List<UserDto> intersDto = new ArrayList<>();
		for(User u: inters){
			List<String> role = new ArrayList<>();
			for(Role roles: roleCusRepo.getRole(userSer.findByUsername(u.getUsername()).get())){
				role.add(roles.getName());
			}
			UserDto user = UserMapper.toUserDto(u);
			user.setRole(role);
			intersDto.add(user);
		}
		return ResponseEntity.ok(intersDto);
	}
	
	@GetMapping("/get_info_byId/{id}")
	@ResponseBody
	public User getAccById(@PathVariable Long id) {
			return userSer.findMenterById(id);
	}

	@PostMapping("/createBlog")
	@ResponseBody
	public ResponseEntity<Object> createBlog(@RequestBody CreatePostRequest createPostRequest,
											 @RequestParam( defaultValue = "", required = false) String username){
		User user = userSer.findByUsername(username).get();
		if(userSer.findByUsername(username).isEmpty()){
			throw new IllegalStateException("User ko ton tai");
		}

		Blog post = userSer.createPost(createPostRequest,user);
		return ResponseEntity.ok(post);
	}

	
//	@DeleteMapping("/delete_user/{email}")
//	public ResponseEntity<?> delByEmail(@PathVariable String email){
//		menterSer.deleteById(menterSer.findByEmail(email).getId());
//		return ResponseEntity.ok("done");
//	}
}
