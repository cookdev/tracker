package org.anyframe.cloud.cargo.tracker.infrastructure.persistence.jpa;

import org.anyframe.cloud.cargo.tracker.domain.model.cargo.TrackingId;
import org.anyframe.cloud.cargo.tracker.domain.model.handling.HandlingEvent;
import org.anyframe.cloud.cargo.tracker.domain.model.handling.HandlingEventRepository;
import org.anyframe.cloud.cargo.tracker.domain.model.handling.HandlingHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.io.Serializable;

@Repository
public class JpaHandlingEventRepository implements HandlingEventRepository, Serializable {
    
	private static final long serialVersionUID = 717844241998626705L;
	
	@Autowired
    private EntityManager entityManager;
	
    @Override
    public void store(HandlingEvent event) {
        entityManager.persist(event);
    }

    @Override
    public HandlingHistory lookupHandlingHistoryOfCargo(TrackingId trackingId) {
        return new HandlingHistory(entityManager.createNamedQuery(
                "HandlingEvent.findByTrackingId", HandlingEvent.class)
                .setParameter("trackingId", trackingId).getResultList());
    }
    
}
