package org.anyframe.cloud.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "registered_user")
public class RegisteredUser {
	
	@Id
	private String id;	

    @Column(unique = true, nullable = false)
	private String loginName;
	
    @Column(unique = true, nullable = false)
	private String emailAddress;
	
	private String firstName;
	
	private String lastName;
	
	private String mobilePhoneNo;
	
	private String createdBy;
	
	public RegisteredUser(){}

	public RegisteredUser(String id, String loginName){
		this.id = id;
		this.loginName = loginName;
	}

	public RegisteredUser(String id, String loginName, String emailAddress){
		this.id = id;
		this.loginName = loginName;
		this.emailAddress = emailAddress;
	}
	
	public RegisteredUser(String id, String loginName, String emailAddress, String firstName,
			String lastName, String mobilePhoneNo) {
		this.id = id;
		this.loginName = loginName;
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobilePhoneNo = mobilePhoneNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobilePhoneNo() {
		return mobilePhoneNo;
	}

	public void setMobilePhoneNo(String mobilePhoneNo) {
		this.mobilePhoneNo = mobilePhoneNo;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
}
