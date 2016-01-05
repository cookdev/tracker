package org.anyframe.cloud.auth.web.rest.dto;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;

public class UserIdRequestDTO {
	
    @Email
    @Size(min = 5, max = 100)
    private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}    
}
