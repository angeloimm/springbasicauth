package it.olegna.test.basic.svc;

import it.olegna.test.basic.models.BasicUser;
import it.olegna.test.basic.models.UserRole;

public interface BasicService {

	void createUser( BasicUser bu );
	void createRole( UserRole ur );
	BasicUser findByUsername( String username );
}
