package com.example.demo.controller;

import com.example.demo.auth.response.ResponseMessage;
import com.example.demo.entity.Inter;
import com.example.demo.entity.Mentor;
import com.example.demo.responsive.InterRepository;
import com.example.demo.responsive.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//import com.example.demo.entity.Inter;
import com.example.demo.entity.User;
import com.example.demo.Dto.mapper.UserMapper;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/inter")
public class InterController {

	@Autowired
	public UserService userSer;

	@Autowired
	MentorRepository menterRepo;

	@Autowired
	InterRepository interRepo;

	@PostMapping("/joinMentor/{username}")
	public ResponseEntity<?> setMentor(@PathVariable(name = "username") String username, @RequestParam(name = "into") String mentorName){
		if(interRepo.findByUsername(username).isEmpty() || menterRepo.findByUsername(mentorName).isEmpty()){
			throw new IllegalStateException("KO tim thay inter hoac menter nay");
		}
		Mentor userMentor = menterRepo.findByUsername(mentorName).get();
		Inter interUser = interRepo.findByUsername(username).get();

		interUser.setMentor(userMentor);
		interRepo.save(interUser);
		return new ResponseEntity<>(new ResponseMessage("Tao danh sach thanh cong!"), HttpStatus.OK);
	}


//	@PostMapping("/createNew")
//	 public ResponseEntity<?> getSignUp(@RequestBody User user){
//		   interSer.checkEmail(user.getEmail());  
//		   interSer.saveUser(user);
//		   return ResponseEntity.ok(user);
//		}
	
//	@PutMapping("/changeInfo/{id}")
//	public ResponseEntity<?> getChange(@PathVariable Long id, @RequestBody RequestChangeU userReq){
//		Inter u = interSer.checkId(id);
////		Inter userUpLoad = new Inter(u.getId(),userReq.getFullName(), userReq.getPhoneNum(),
////				u.getEmail(), u.getPassWord(), userReq.getRole());
//		
//		
//		interSer.save(userUpLoad);
//		return ResponseEntity.ok(InterMapper.toUserDto(userUpLoad));
//	}

	
 }
