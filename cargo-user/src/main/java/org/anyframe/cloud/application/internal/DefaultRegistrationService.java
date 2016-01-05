package org.anyframe.cloud.application.internal;

import org.anyframe.cloud.application.CommonService;
import org.anyframe.cloud.application.RegistrationService;
import org.anyframe.cloud.domain.RegisteredUser;
import org.anyframe.cloud.domain.UserAccount;
import org.anyframe.cloud.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//import org.anyframe.cloud.application.exception._PasswordNotValid;

@Service
public class DefaultRegistrationService extends CommonService implements RegistrationService {
	
	@Autowired
	private RestTemplate springRestTemplate;

	@Override
	public String registerNewUser(RegisteredUser registeredUser, String password) {

		registeredUser.setId(IdGenerator.generateId());
		
		//Publish Event or Rest Call for Auth Server
		UserAccount userAccount = new UserAccount(registeredUser.getLoginName(), password, registeredUser.getEmailAddress());
		HttpEntity<UserAccount> requestEntity = new HttpEntity<UserAccount>(userAccount);
		ResponseEntity<String> result = null;
		result = springRestTemplate.exchange("http://70.121.244.13:8070/auth/api/register", HttpMethod.POST, requestEntity, String.class);

		if(!result.getStatusCode().is2xxSuccessful()){
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + result.getBody());
		}
		
		registeredUserRepository.save(registeredUser);
		
		return registeredUser.getId();
	}
	
	@Override
	public void withdrawalUser(RegisteredUser registeredUser) {
		
		RegisteredUser target = registeredUserRepository.findByEmailAddress(registeredUser.getEmailAddress());
		registeredUserRepository.delete(target.getId());
		
	}

	@Override
	public RegisteredUser isRegistered(String emailAddress) {
		
		RegisteredUser target = registeredUserRepository.findByEmailAddress(emailAddress);
		
		return target;
	}

}
