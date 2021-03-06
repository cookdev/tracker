package org.anyframe.cloud.cargo.user.interfaces.facade;

import org.anyframe.cloud.cargo.user.interfaces.facade.dto.*;


public interface UserServiceFacade {

	UserResponse registerNewUser(RegistrationRequest request);

	void withdrawalUser(UserAccountRequest request);

	IsRegistered isRegistered(String emailAddress);

	UserResponse getUser(String loginName);

	UserResponse getUserByEmail(String emailAddress);

	UsersResponse getUsers();

	UsersResponse getUsersByRole(String role);

//	UserResponse login(UserAccountRequest request);

//	void logout(UserAccountRequest request);

	UserResponse modifyUser(RegistrationRequest request);

//	void changePassword(UserAccountRequest request);

}
