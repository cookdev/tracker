package org.anyframe.cloud.cargo.user.domain;

public class UserAccount {

	private String login;
	
	private String password;
	
	private String email;
	
	public UserAccount(){
	}
	
	public UserAccount(String login, String password, String email) {
		super();
		this.login = login;
		this.password = password;
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
