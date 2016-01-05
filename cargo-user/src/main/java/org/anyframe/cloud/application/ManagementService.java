package org.anyframe.cloud.application;

import org.anyframe.cloud.domain.RegisteredUser;

import java.util.List;


/**
 * Cargo Tracker Management Portal's User Management service
 * @author Hahn
 */
public interface ManagementService {

	RegisteredUser getUser(String loginName);
	
	RegisteredUser getUserByEmailAddress(String emailAddress);
	
	RegisteredUser getUserById(String id);

	List<RegisteredUser> getUserList();

	void modifyUser(RegisteredUser registeredUser);

//	void changePassword(RegisteredUser registeredUser);
}
