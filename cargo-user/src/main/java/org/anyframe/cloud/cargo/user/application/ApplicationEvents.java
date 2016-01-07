package org.anyframe.cloud.cargo.user.application;

import org.anyframe.cloud.cargo.user.domain.RegisteredUser;

public interface ApplicationEvents {

	void userRegistered(RegisteredUser registeredUser);
}
