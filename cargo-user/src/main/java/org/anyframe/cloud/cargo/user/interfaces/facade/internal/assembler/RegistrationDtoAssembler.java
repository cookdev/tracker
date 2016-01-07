package org.anyframe.cloud.cargo.user.interfaces.facade.internal.assembler;

import org.anyframe.cloud.cargo.user.domain.RegisteredUser;
import org.anyframe.cloud.cargo.user.interfaces.facade.dto.*;

import java.util.ArrayList;
import java.util.List;


// TODO Convert to a singleton and inject?
public class RegistrationDtoAssembler {
	
	public UsersResponse listToDto(List<RegisteredUser> registeredUserList) {
		List<UserResponse> usersResponse = new ArrayList<UserResponse>();
		
		for(RegisteredUser user : registeredUserList){
			usersResponse.add(this.toDto(user));
		}
		return new UsersResponse(usersResponse);
	}

    public UserResponse toDto(RegisteredUser registeredUser) {
    	return new UserResponse(
                registeredUser.getId(),
                registeredUser.getLoginName(),
                registeredUser.getEmailAddress(),
                registeredUser.getFirstName(),
                registeredUser.getLastName(),
                registeredUser.getMobilePhoneNo());
    }

    public IsRegistered toDtoIsRegistered(RegisteredUser registeredUser) {
    	boolean isRegistered = false;
    	if(registeredUser != null && registeredUser.getId() != null){
    		isRegistered = true;
    	}
    	return new IsRegistered(isRegistered);
    }
    
    public RegisteredUser fromDto(String id, RegistrationRequest registrationRequest){
    	return new RegisteredUser(id,
    			registrationRequest.getLoginName(),
    			registrationRequest.getEmailAddress(),
    			registrationRequest.getFirstName(),
    			registrationRequest.getLastName(),
    			registrationRequest.getMobilePhoneNo());
    }

	public RegisteredUser fromDtoUserAccount(UserAccountRequest request) {
    	return new RegisteredUser(request.getId(), request.getEmailAddress());
	}
}
