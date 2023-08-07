package com.example.demo.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	private String name;

	
	public Role( String nameRole) {
		super();
		this.name = nameRole;
	}
	
	
}
