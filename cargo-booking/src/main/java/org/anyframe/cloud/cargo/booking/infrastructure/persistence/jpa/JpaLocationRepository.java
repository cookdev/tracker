package org.anyframe.cloud.cargo.booking.infrastructure.persistence.jpa;

import org.anyframe.cloud.cargo.booking.domain.model.location.Location;
import org.anyframe.cloud.cargo.booking.domain.model.location.LocationRepository;
import org.anyframe.cloud.cargo.booking.domain.model.location.UnLocode;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Repository
public class JpaLocationRepository implements LocationRepository, Serializable {

    private static final long serialVersionUID = 1L;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Location find(UnLocode unLocode) {
        return entityManager.createNamedQuery("Location.findByUnLocode",
                Location.class).setParameter("unLocode", unLocode)
                .getSingleResult();
    }

    @Override
    public List<Location> findAll() {
        return entityManager.createNamedQuery("Location.findAll", Location.class)
                .getResultList();
    }
}
