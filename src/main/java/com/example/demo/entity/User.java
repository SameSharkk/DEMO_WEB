package com.example.demo.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "user")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	private String name;
	private String username;
	private String email;
	private String password;
	private Long totalView;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "user_lang", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "language_id"))
//	private Set<Language> languages = new HashSet<>();

	public User(String name, String username, String email, String password) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		roles.stream().forEach(i->authorities.add(new SimpleGrantedAuthority(i.getName())));
		return List.of(new SimpleGrantedAuthority(authorities.toString()));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
