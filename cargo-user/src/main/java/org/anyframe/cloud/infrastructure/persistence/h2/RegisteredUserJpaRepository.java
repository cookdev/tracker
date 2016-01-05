package org.anyframe.cloud.infrastructure.persistence.h2;

import org.anyframe.cloud.domain.RegisteredUser;
import org.anyframe.cloud.infrastructure.persistence.RegisteredUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RegisteredUserJpaRepository extends RegisteredUserRepository, JpaRepository<RegisteredUser, String> {
}
