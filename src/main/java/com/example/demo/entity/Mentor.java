package com.example.demo.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "mentor")
public class Mentor {
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User users ;

	@OneToMany(mappedBy = "mentor")
	private List<Inter> inters;

	public Mentor(User users){
		this.users = users;
	}
}


//public class Menter extends User {
//	@Id
//	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
//	private Long id;
//
//	// Menter specific columns
//
//	@ManyToMany
//	@JoinTable(name = "menter_lang", joinColumns = @JoinColumn(name = "menter_id"), inverseJoinColumns = @JoinColumn(name = "language_id"))
//	private Set<Language> languages = new HashSet<>();
//
//	@OneToMany(mappedBy = "menter")
//	private List<Inter> inters;
//
//	public Menter (Long id , String fullName, String phoneNum, String email, String passWord, String role, Set<Language> languages) {
//		super(id, fullName, phoneNum, email, passWord);
//		super.setRole("ADMIN");
//	}
//	
//}
