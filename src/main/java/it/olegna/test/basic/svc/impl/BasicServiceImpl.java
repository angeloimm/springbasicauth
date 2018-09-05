package it.olegna.test.basic.svc.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.olegna.test.basic.dao.BasicUserDao;
import it.olegna.test.basic.dao.RoleDao;
import it.olegna.test.basic.models.BasicUser;
import it.olegna.test.basic.models.UserRole;
import it.olegna.test.basic.svc.BasicService;

@Service
public class BasicServiceImpl implements BasicService {
	private static final Logger logger = LoggerFactory.getLogger(BasicServiceImpl.class.getName());
	@Autowired
	private BasicUserDao bud;
	@Autowired
	private RoleDao rd;
	@Override
	@Transactional(transactionManager = "hibTx", rollbackFor = Exception.class, readOnly = false) 
	public void createUser(BasicUser bu) {
		try {
			bud.persist(bu);
		} catch (Exception e) {
			logger.error("Error user creation", e);
			throw e;
		}

	}

	@Override
	@Transactional(transactionManager = "hibTx", rollbackFor = Exception.class, readOnly = false) 
	public void createRole(UserRole ur) {
		try {
			rd.persist(ur);
		} catch (Exception e) {
			logger.error("Error role creation", e);
			throw e;
		}

	}

	@Override
	@Transactional(transactionManager = "hibTx", rollbackFor = Exception.class, readOnly = true) 
	public BasicUser findByUsername(String username) {
		try {
			
			return bud.findByUsername(username);
		} catch (Exception e) {
			logger.error("Error user finding", e);
			throw e;
		}
	}

}
