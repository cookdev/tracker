package org.anyframe.cloud.cargo.tracker.application;

import org.anyframe.cloud.cargo.tracker.domain.model.cargo.TrackingId;
import org.anyframe.cloud.cargo.tracker.domain.model.handling.CannotCreateHandlingEventException;
import org.anyframe.cloud.cargo.tracker.domain.model.handling.HandlingEvent;
import org.anyframe.cloud.cargo.tracker.domain.model.location.UnLocode;
import org.anyframe.cloud.cargo.tracker.domain.model.voyage.VoyageNumber;

import java.util.Date;

public interface HandlingEventService {

    /**
     * Registers a handling event in the system, and notifies interested parties
     * that a cargo has been handled.
     */
    void registerHandlingEvent(Date completionTime,
            TrackingId trackingId,
            VoyageNumber voyageNumber,
            UnLocode unLocode,
            HandlingEvent.Type type) throws CannotCreateHandlingEventException;
}
