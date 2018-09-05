package it.olegna.test.basic.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import it.olegna.test.basic.models.BasicUser;

@Repository
public class BasicUserDao extends AbstractDao<String, BasicUser> {

	@Override
	protected Class<BasicUser> getPersistentClass() {
		return BasicUser.class;
	}
	public BasicUser findByUsername( String username ) {
		CriteriaBuilder cb = createCriteriaBuilder();
		CriteriaQuery<BasicUser> query = cb.createQuery(BasicUser.class);
		Root<BasicUser> root = query.from(BasicUser.class);
		root.fetch("authorities", JoinType.INNER);
		query.select(root);
		query.where(cb.equal(root.get("username"), username));
		BasicUser result = getSession().createQuery(query).getSingleResult();
		return result;
	}
}
