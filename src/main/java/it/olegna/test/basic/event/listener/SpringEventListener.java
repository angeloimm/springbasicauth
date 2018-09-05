package it.olegna.test.basic.event.listener;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import it.olegna.test.basic.models.BasicUser;
import it.olegna.test.basic.models.UserRole;
import it.olegna.test.basic.svc.BasicService;

@Component
public class SpringEventListener {
	@Autowired
	private BasicService bs;
	private static final Logger logger = LoggerFactory.getLogger(SpringEventListener.class.getName());
	@EventListener
	public void handleRefreshCtx( ContextRefreshedEvent cre )
	{
		try {
			UserRole ur = new UserRole();
			ur.setName("USER");
			bs.createRole(ur);
			BasicUser bu = new BasicUser();
			bu.setAccountExpired(false);
			bu.setAccountLocked(false);
			bu.setCredentialsExpired(false);
			bu.setEnabled(true);
			bu.setAuthorities(Collections.singletonList(ur));
			bu.setUsername("test");
			bu.setPassword("testpwd");
			this.bs.createUser(bu);
		} catch (Exception e) {
			logger.error("Error in ctx refreshing", e);
		}
	}
}
