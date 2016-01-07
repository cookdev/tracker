package org.anyframe.cloud.cargo.user.infrastructure.persistence.h2;

import org.anyframe.cloud.cargo.user.infrastructure.persistence.RegisteredUserRepository;
import org.anyframe.cloud.cargo.user.domain.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RegisteredUserJpaRepository extends RegisteredUserRepository, JpaRepository<RegisteredUser, String> {
}
