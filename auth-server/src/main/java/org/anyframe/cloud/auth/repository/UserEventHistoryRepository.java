package org.anyframe.cloud.auth.repository;

import org.anyframe.cloud.auth.domain.UserEventHistory;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserEventHistoryRepository extends JpaRepository<UserEventHistory, Long> {

}
