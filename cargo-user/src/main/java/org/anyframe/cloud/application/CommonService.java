package org.anyframe.cloud.application;

import org.anyframe.cloud.infrastructure.persistence.h2.RegisteredUserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommonService {

	@Autowired
	protected RegisteredUserJpaRepository registeredUserRepository;

//	public boolean isValidPassword(String emailAddress, String password){
//		RegisteredUser target = registeredUserRepository.findByEmailAddress(emailAddress);
//		if(target == null){
//			throw new ContentsNotExistException("User is not existed", null);
//		}else if(password.equals(target.getPassword())){
//			return true;
//		}else{
//			return false;
//		}
//	}

}
