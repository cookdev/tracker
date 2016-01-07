package org.anyframe.cloud.cargo.user.infrastructure.persistence;

import org.anyframe.cloud.cargo.user.domain.RegisteredUser;


public interface RegisteredUserRepository {
	
	RegisteredUser findByLoginName(String loginName);
	
	RegisteredUser findByEmailAddress(String emailAddress);
	
}
