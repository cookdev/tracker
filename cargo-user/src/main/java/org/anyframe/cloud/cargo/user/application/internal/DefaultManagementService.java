package org.anyframe.cloud.cargo.user.application.internal;

import org.anyframe.cloud.cargo.user.application.CommonService;
import org.anyframe.cloud.cargo.user.application.ManagementService;
import org.anyframe.cloud.cargo.user.application.exception.ContentsNotExistException;
import org.anyframe.cloud.cargo.user.domain.RegisteredUser;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Cargo Tracker Management Portal's User Management service
 * @author Hahn
 */
@Service
public class DefaultManagementService extends CommonService implements ManagementService{

	public RegisteredUser getUser(String loginName){
		
		RegisteredUser registeredUser = registeredUserRepository.findByLoginName(loginName);
		
		if(registeredUser == null){
			throw new ContentsNotExistException("Username("+loginName+") is not existed", null);
		}
		
		return registeredUser; 
		
	}

	public RegisteredUser getUserByEmailAddress(String emailAddress){
		
		RegisteredUser registeredUser = registeredUserRepository.findByEmailAddress(emailAddress);
		
		if(registeredUser == null){
			throw new ContentsNotExistException("EmailAddress("+emailAddress+") is not existed", null);
		}
		
		return registeredUser; 
		
	}

	public RegisteredUser getUserById(String id){
		RegisteredUser registeredUser = registeredUserRepository.findOne(id);

		if(registeredUser == null){
			throw new ContentsNotExistException("ID("+id+") is not existed", null);
		}
		
		return registeredUser;
		
	}

	@Override
	public List<RegisteredUser> getUserList() {

		List<RegisteredUser> registeredUserList = registeredUserRepository.findAll();
		if(registeredUserList == null || registeredUserList.size() == 0){
			throw new ContentsNotExistException("User is not existed", null);
		}
		
		return registeredUserList;
	}

	@Override
	public void modifyUser(RegisteredUser registeredUser) {
		
		RegisteredUser before = registeredUserRepository.findByLoginName(registeredUser.getLoginName());
		registeredUser.setId(before.getId());
		
		registeredUserRepository.save(registeredUser);
	}

//	@Override
//	public void changePassword(RegisteredUser registeredUser) {
//		
//		RegisteredUser before = registeredUserRepository.findByEmailAddress(registeredUser.getEmailAddress());
//		registeredUser.setId(before.getId());
//		registeredUser.setFirstName(before.getFirstName());
//		registeredUser.setLastName(before.getLastName());
//		registeredUser.setRole(before.getRole());
//		registeredUser.setCompany(before.getCompany());
//		
//		registeredUserRepository.save(registeredUser);
//		
//	}
}
