package org.anyframe.cloud.cargo.tracker.domain.model.handling;

import org.anyframe.cloud.cargo.tracker.domain.model.cargo.TrackingId;

public interface HandlingEventRepository {

    void store(HandlingEvent event);

    HandlingHistory lookupHandlingHistoryOfCargo(TrackingId trackingId);
}
