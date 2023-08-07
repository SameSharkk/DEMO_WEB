package com.example.demo.Dto.mapper;

import com.example.demo.Dto.UserDto;
//import com.example.demo.entity.Inter;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.responsive.RoleCustomRepo;
import com.example.demo.responsive.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

	public static UserDto toUserDto(User user) {
		UserDto userMap = new UserDto();
		userMap.setName(user.getName());
		userMap.setUsername(user.getUsername());
		userMap.setEmail(user.getEmail());
		return userMap;
	}
}

