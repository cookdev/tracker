package org.anyframe.cloud.cargo.tracker.infrastructure.persistence.jpa;

import org.anyframe.cloud.cargo.tracker.domain.model.voyage.Voyage;
import org.anyframe.cloud.cargo.tracker.domain.model.voyage.VoyageNumber;
import org.anyframe.cloud.cargo.tracker.domain.model.voyage.VoyageRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

@Repository
public class JpaVoyageRepository implements VoyageRepository, Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Voyage find(VoyageNumber voyageNumber) {
		return entityManager
				.createNamedQuery("Voyage.findByVoyageNumber", Voyage.class)
				.setParameter("voyageNumber", voyageNumber).getSingleResult();
	}
}
