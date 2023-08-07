package com.example.demo.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
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
@Table(name = "inter")
public class Inter {
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User users ;

	@ManyToOne
	@JoinColumn(name = "mentor_id")
	private Mentor mentor;

	public Inter(User users){
		this.users = users;
	}
	
}


//public class Inter extends User {
//	@Id
//	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
//	private Long id;
//
//
//	// Inter specific columns
//
//	@ManyToOne
//	@JoinColumn(name = "menter_id")
//	private Menter menter;
//
//	public Inter (Long id , String fullName, String phoneNum, String email, String passWord, String role) {
//		super(id, fullName, phoneNum, email, passWord);
//		super.setRole("USER");
//	}
//}
