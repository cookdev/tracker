package org.anyframe.cloud.cargo.user.application;

import org.anyframe.cloud.cargo.user.domain.RegisteredUser;

/**
 * Cargo Tracker Management Portal's User Resgistration service
 * @author Hahn
 */
public interface RegistrationService {

	String registerNewUser(RegisteredUser registeredUser, String password);

	void withdrawalUser(RegisteredUser registeredUser);

	RegisteredUser isRegistered(String emailAddress);
	
}
