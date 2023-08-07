package com.example.demo.security.jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.auth.request.SignInForm;
import com.example.demo.auth.response.tokenResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.responsive.RoleCustomRepo;
import com.example.demo.responsive.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepository userRepo;
	@Autowired
	AuthenticationManager authenticationManager;
	private final RoleCustomRepo roleRepo;
	private final JwtService jwtSer;
	
	public tokenResponse authenticate(SignInForm authReq) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword()));
		User user = userRepo.findByUsername(authReq.getUsername()).orElseThrow(()->new UsernameNotFoundException("User khong tim thay 222"));
		List<Role> roles = null;
		if(user!= null) {
			roles = roleRepo.getRole(user);
		}
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		Set<Role> set = new HashSet<>();
		roles.stream().forEach(c->set.add(new Role(c.getName())));
		user.setRoles(set);
		set.stream().forEach(i->authorities.add(new SimpleGrantedAuthority(i.getName())));
		var jwtToken = jwtSer.generateToken(user, authorities);
		var jwtRefreshToken = jwtSer.generateRefreshToken(user, authorities);
		return tokenResponse.builder().token(jwtToken).refreshToken(jwtRefreshToken).build();
	}
}
