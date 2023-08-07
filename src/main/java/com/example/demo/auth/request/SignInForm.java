package com.example.demo.auth.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SignInForm {
	private String username;
	private String password;

}
