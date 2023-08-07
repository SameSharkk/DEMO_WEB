package com.example.demo.responsive;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class RoleCustomRepo {
		
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("deprecation")
	public List<Role> getRole(User user){
		StringBuilder sql = new StringBuilder()
				.append("select r.name from shopsame.user u join shopsame.user_role ur on u.id = ur.user_id "
						+ "join shopsame.roles r on r.id = ur.role_id ");
		sql.append("where 1=1 ");
		if(user.getUsername()!=null) {
			sql.append("and username = :username");
		}
		
		NativeQuery<Role> query =((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());
				
		if(user.getUsername()!=null) {
			query.setParameter("username", user.getUsername());
		}
		
		query.addScalar("name", StandardBasicTypes.STRING);
		query.setResultTransformer(Transformers.aliasToBean(Role.class));
		return query.list();
	}
}
