package org.anyframe.cloud.cargo.tracker.domain.model.voyage;

public interface VoyageRepository {

    Voyage find(VoyageNumber voyageNumber);
}
