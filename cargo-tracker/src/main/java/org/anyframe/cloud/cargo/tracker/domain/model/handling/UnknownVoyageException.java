package org.anyframe.cloud.cargo.tracker.domain.model.handling;

import org.anyframe.cloud.cargo.tracker.domain.model.voyage.VoyageNumber;

/**
 * Thrown when trying to register an event with an unknown carrier movement id.
 */
public class UnknownVoyageException extends CannotCreateHandlingEventException {

    private static final long serialVersionUID = 1L;
    private final VoyageNumber voyageNumber;

    public UnknownVoyageException(VoyageNumber voyageNumber) {
        this.voyageNumber = voyageNumber;
    }

    @Override
    public String getMessage() {
        return "No voyage with number " + voyageNumber.getIdString()
                + " exists in the system";
    }
}
