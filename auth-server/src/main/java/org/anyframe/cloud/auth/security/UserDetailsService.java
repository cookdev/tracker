package org.anyframe.cloud.auth.security;

import org.anyframe.cloud.auth.domain.User;
import org.anyframe.cloud.auth.repository.UserRepository;
import org.anyframe.cloud.auth.domain.Authority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

	private static final int LOGIN_FAILURE_COUNT_THAT_YOUR_ACCOUNT_BE_LOCKED = 5;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true; 
        boolean accountNonLocked = true;
    	
        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase();
        User userFromDatabase = userRepository.findOneByLogin(lowercaseLogin);
        if (userFromDatabase == null) {
            throw new UsernameNotFoundException("User is not found in the database");
        } else if(!userFromDatabase.getActivated()) {
            throw new UserNotActivatedException("User is not activated");
        } else if(userFromDatabase.getLoginFailureCount() != null 
        		&& userFromDatabase.getLoginFailureCount() >= LOGIN_FAILURE_COUNT_THAT_YOUR_ACCOUNT_BE_LOCKED ) {
        	accountNonLocked = false;
        }

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : userFromDatabase.getAuthorities()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
            grantedAuthorities.add(grantedAuthority);
        }
        return new org.springframework.security.core.userdetails.User(lowercaseLogin,
            userFromDatabase.getPassword(), 
            enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
            grantedAuthorities);
    }
}
