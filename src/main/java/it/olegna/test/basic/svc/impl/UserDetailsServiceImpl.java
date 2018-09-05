package it.olegna.test.basic.svc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.olegna.test.basic.models.BasicUser;
import it.olegna.test.basic.svc.BasicService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private BasicService svc;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		BasicUser result = svc.findByUsername(username);
		if( result == null )
		{
			throw new UsernameNotFoundException("No user found with username "+username);
		}
		return result;
	}

}
