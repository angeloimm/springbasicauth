package it.olegna.test.basic.dao;
import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.olegna.test.basic.models.AbstractModel;
public abstract class AbstractDao<PK extends Serializable, T extends AbstractModel> {
	@Autowired
	private SessionFactory sf;
	protected abstract Class<T> getPersistentClass();
	protected Session getSession()
	{
		return sf.getCurrentSession();
	}
	protected CriteriaBuilder createCriteriaBuilder()
	{
		return getSession().getCriteriaBuilder();
	}
	public List<T> findAll()
	{
		CriteriaBuilder cb = createCriteriaBuilder();
		CriteriaQuery<T> rootQery = cb.createQuery(getPersistentClass());
		Root<T> rootEntry = rootQery.from(getPersistentClass());
		CriteriaQuery<T> all = rootQery.select(rootEntry);
		TypedQuery<T> allQuery = getSession().createQuery(all);
		return allQuery.getResultList();
	}
	public Long count()
	{
		CriteriaBuilder qb = createCriteriaBuilder();
		CriteriaQuery<Long> cq = qb.createQuery(Long.class);
		cq.select(qb.count(cq.from(getPersistentClass())));
		return getSession().createQuery(cq).getSingleResult();
	}
	
	public T getByKey(PK key)
	{
		return (T) getSession().get(getPersistentClass(), key);
	}
	public T loadByKey(PK key)
	{
		return (T) getSession().load(getPersistentClass(), key);
	}
	public void persist(T entity)
	{
		getSession().persist(entity);
	}
	public void persistFlush(T entity)
	{
		getSession().persist(entity);
		getSession().flush();
	}
}
