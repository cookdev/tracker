package org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * DTO for presenting and selecting an itinerary from a collection of
 * candidates.
 */
public class RouteCandidate implements Serializable {

	private static final long serialVersionUID = -3109776279294054964L;
	
	private List<Leg> legs;

    public RouteCandidate(List<Leg> legs) {
        this.legs = legs;
    }

    public List<Leg> getLegs() {
        return Collections.unmodifiableList(legs);
    }

    @Override
    public String toString() {
        return "RouteCandidate{" + "legs=" + legs + '}';
    }
}