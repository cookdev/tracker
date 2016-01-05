package org.anyframe.cloud.auth.repository;

import org.anyframe.cloud.auth.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
	
	List<Authority> findByNameStartingWith(String name);
}
