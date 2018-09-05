package it.olegna.test.basic.dao;

import org.springframework.stereotype.Repository;

import it.olegna.test.basic.models.UserRole;

@Repository
public class RoleDao extends AbstractDao<String, UserRole> {

	@Override
	protected Class<UserRole> getPersistentClass() {
		return UserRole.class;
	}

}
