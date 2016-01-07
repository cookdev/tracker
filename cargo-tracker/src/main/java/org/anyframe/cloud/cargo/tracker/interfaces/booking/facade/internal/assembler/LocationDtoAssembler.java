package org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.internal.assembler;

import org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.dto.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LocationDtoAssembler {

    public Location toDto(
            org.anyframe.cloud.cargo.tracker.domain.model.location.Location location) {
        return new Location(
                location.getUnLocode().getIdString(), location.getName());
    }

    public List<Location> toDtoList(
            List<org.anyframe.cloud.cargo.tracker.domain.model.location.Location> allLocations) {
        List<Location> dtoList = new ArrayList<>(
                allLocations.size());

        for (org.anyframe.cloud.cargo.tracker.domain.model.location.Location location : allLocations) {
            dtoList.add(toDto(location));
        }

        Collections.sort(
                dtoList,
                new Comparator<Location>() {

                    @Override
                    public int compare(
                    		Location location1,
                    		Location location2) {
                                return location1.getName().compareTo(location2.getName());
                            }
                });

        return dtoList;
    }
}
