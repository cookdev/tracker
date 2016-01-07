package org.anyframe.cloud.cargo.tracker.application;

import org.anyframe.cloud.cargo.tracker.domain.model.cargo.Cargo;
import org.anyframe.cloud.cargo.tracker.domain.model.handling.HandlingEvent;
import org.anyframe.cloud.cargo.tracker.interfaces.handling.HandlingEventRegistrationAttempt;

/**
 * This interface provides a way to let other parts of the system know about
 * events that have occurred.
 * <p/>
 * It may be implemented synchronously or asynchronously, using for example JMS.
 */
public interface ApplicationEvents {

    void cargoWasHandled(HandlingEvent event);

    void cargoWasMisdirected(Cargo cargo);

    void cargoHasArrived(Cargo cargo);

    void receivedHandlingEventRegistrationAttempt(HandlingEventRegistrationAttempt attempt);
    
}
