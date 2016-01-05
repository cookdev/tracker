package org.anyframe.cloud.auth.repository;

import org.anyframe.cloud.auth.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Spring Data JPA repository for the User entity.
 */
public interface ClientRepository extends JpaRepository<Client, String> {

}
