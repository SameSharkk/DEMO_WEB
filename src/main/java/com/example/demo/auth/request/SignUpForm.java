package com.example.demo.auth.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SignUpForm {
    private String name;
    private String username;
    private String email;
    private String password;
    private Set<String> roles;
}