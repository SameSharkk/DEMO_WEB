package com.example.demo.service.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.Dto.ChangePasswordRequest;
import com.example.demo.Dto.CreateCommentPostRequest;
import com.example.demo.Dto.CreatePostRequest;
import com.example.demo.Dto.mapper.UserMapper;
import com.example.demo.entity.Blog;
import com.example.demo.entity.Comment;
import com.example.demo.responsive.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.UserDto;
//import com.example.demo.entity.Inter;
import com.example.demo.entity.Role;
//import com.example.demo.entity.Menter;
import com.example.demo.entity.User;
//import com.example.demo.responsive.InterResponsive;
//import com.example.demo.responsive.MenterResponsive;
import com.example.demo.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{
	
	@Autowired
	public InterRepository interRepo;
	
	@Autowired
	public MentorRepository menterRepo;
	
	@Autowired
	public UserRepository userRepo;

	
	@Autowired
	public RoleRepository roleRepo;
	
	@Autowired
	public RoleCustomRepo roleCustomRepo;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	BlogRepository blogRepo;
	
	private final PasswordEncoder passwordEncoder;


	@Override
	public User updateProfile(User inter) {
		userRepo.save(inter);
		return inter;
	}

	@Override
	public User changePassword(String newPassword) {
		return null;
	}
	
	@Override
	public List<User> findInterAllById(List<Long> ids) {
		return userRepo.findAllById(ids);
	}

	@Override
	public User findInterById(Long id) {
		Optional<User> userO = userRepo.findById(id);
		if(userO.isPresent()) {
			return userO.get();
		}
		else {
			throw new IllegalStateException("Khong tim thay ID");
		}
	}
	

	@Override
	public User findMenterById(Long id) {
		Optional<User> userO = userRepo.findById(id);
		if(userO.isPresent()) {
			return userO.get();
		}
		else {
			throw new IllegalStateException("Khong tim thay ID");
		}
	}


	@Override
	public List<UserDto> findAll() {
		List<UserDto> uDto = new ArrayList<UserDto>();
		for(User user : userRepo.findAll()) {
			uDto.add(UserMapper.toUserDto(user));
		}
		return uDto;
	}

	
	@Override
	public void deleteById(Long id) {
		userRepo.deleteById(id);
	}
	

	@Override
	public void checkEmail(String email) {
		for(String e : userRepo.getListEmail()) {
			if(email.equals(e)) {
				throw new IllegalStateException("email da ton taiS");
			}
		}
	}

	
	@Override
	public User saveUser(User entity) {
		return userRepo.save(entity);
	}

	@Override
	public void addLang(String email, String langName) {

	}

	
	@Override
	public List<Role> getByEmail(User user) {
		List<Role> roles = roleCustomRepo.getRole(user);
		return roles;
	}

    @Override
    public Boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

	@Override
	public Role saveRole(Role role) {
		return roleRepo.save(role);
		
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public User findById(Long id) {
		User user = userRepo.findById(id).get();
		user.setTotalView(user.getTotalView() + 1);
		userRepo.save(user);
		return user;
	}

	@Override
	public void changePassword(User user, ChangePasswordRequest changePasswordRequest) {
		//Kiểm tra mật khẩu
		if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
			throw new IllegalStateException("Mật khẩu cũ không chính xác");
		}

		String newPassword = changePasswordRequest.getNewPassword();
		// Mã hóa mật khẩu mới
		String encodedPassword = passwordEncoder.encode(newPassword);
		user.setPassword(encodedPassword);
		userRepo.save(user);
	}


	@Override
	public List<UserDto> getListView() {
		List<UserDto> mentorInfoDTOS = new ArrayList<>();
		for(User user : userRepo.getListView(3)){
			mentorInfoDTOS.add(UserMapper.toUserDto(user));
		}
		return mentorInfoDTOS;
	}

	@Override
	public List<UserDto> findAllMentor() {
		List<UserDto> users = new ArrayList<>();
		for(User user : userRepo.findAllMentor()){
			users.add(UserMapper.toUserDto(user));
		}
		return users;
	}

	@Override
	public List<UserDto> findByKeyword(String keyword) {
		if (keyword == null) {
			keyword = "";
		}
		List<UserDto> users = new ArrayList<>();
		for(User user : userRepo.searchByKeyword(keyword)){
			users.add(UserMapper.toUserDto(user));
		}
		return users;
	}

	@Override
	public Blog createPost(CreatePostRequest createPostRequest, User user){
		Blog post = new Blog();
		post.setTitle(createPostRequest.getTitle());
		post.setContent(createPostRequest.getContent());
		post.setCreatedBy(user);
		blogRepo.save(post);
		return post;
	}

	@Override
	public Comment createCommentPost(CreateCommentPostRequest createCommentPostRequest, long userId) {
		Comment comment = new Comment();
		Blog post = new Blog();
		post.setId(createCommentPostRequest.getPostId());
		comment.setBlog(post);
		User user = new User();
		user.setId(userId);
		comment.setUser(user);
		comment.setContent(createCommentPostRequest.getContent());
		try {
			commentRepository.save(comment);
		} catch (Exception e) {
			throw new IllegalStateException("Có lỗi trong quá trình bình luận!");
		}
		return comment;
	}



	//	@Override
//	public void addRole(String email, String roleName) {
//		User m = userRepo.findByEmail(email).get();
//		Optional<Role> roleCheck = roleRepo.findByName(roleName);
//		if(roleCheck.isPresent()) {
//			m.getRoles().add(roleCheck.get());
//		} else {
//			throw new IllegalStateException("Role khong ton tai");
//		}
//
//	}

//	@Override
//	public void addInter(String username) {
//		if(interRepo.checkInterUserName(username).isPresent()) {
//			throw new IllegalStateException("User da la Inter, khong can them nua !!");
//		}
//
//		Inter i = new Inter();
//		Optional<String> emailCheck = menterRepo.checkMenterEmail(email);
//		if(emailCheck.isEmpty()) {
//			i.setUsers(userRepo.findByUsername(username).get());
//			interRepo.save(i);
//		} else {
//			throw new IllegalStateException("User da co vai tro Menter");
//		}
//
//	}

//	@Override
//	public void addMenter(String email) {
//		if(menterRepo.checkMenterEmail(email).isPresent()) {
//			throw new IllegalStateException("User da la Menter, khong can them nua !!");
//		}
//		Menter m = new Menter();
//		Optional<String> emailCheck = interRepo.checkInterEmail(email);
//		if(emailCheck.isEmpty()) {
//			m.setUsers(userRepo.findByEmail(email).get());
//			menterRepo.save(m);
//		} else {
//			throw new IllegalStateException("User da co vai tro Inter");
//		}
//
//	}
//
// 	@Override
//	public User getProfile(String email) {
//	    try {
//	        User i = userRepo.findByEmail(email).get();
//	        return i;
//	      } catch (Exception e) {
//	        throw new IllegalStateException("Error getting profile: {}" + e.getMessage());
//	      }
//	}


}
