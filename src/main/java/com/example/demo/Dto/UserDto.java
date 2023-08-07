package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
	private String name;
	private String username;
	private String email;
	private List<String> role;

	public UserDto(String name, String username, String email){
		this.name = name;
		this.username = username;
		this.email = email;
	}
}

