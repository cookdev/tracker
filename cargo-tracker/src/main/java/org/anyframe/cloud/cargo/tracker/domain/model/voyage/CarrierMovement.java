package org.anyframe.cloud.cargo.tracker.domain.model.voyage;

import org.anyframe.cloud.cargo.tracker.domain.model.location.Location;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * A carrier movement is a vessel voyage from one location to another.
 */
@Entity
@Table(name = "carrier_movement")
public class CarrierMovement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "departure_location_id")
    @NotNull
    private Location departureLocation;
    @ManyToOne
    @JoinColumn(name = "arrival_location_id")
    @NotNull
    private Location arrivalLocation;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "departure_time")
    @NotNull
    private Date departureTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "arrival_time")
    @NotNull
    private Date arrivalTime;
    // Null object pattern
    public static final CarrierMovement NONE = new CarrierMovement(
            Location.UNKNOWN, Location.UNKNOWN, new Date(0), new Date(0));

    public CarrierMovement() {
        // Nothing to initialize.
    }

    public CarrierMovement(Location departureLocation,
            Location arrivalLocation, Date departureTime, Date arrivalTime) {
//        Validate.noNullElements(new Object[]{departureLocation,
//            arrivalLocation, departureTime, arrivalTime});
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
    }

    public Location getDepartureLocation() {
        return departureLocation;
    }

    public Location getArrivalLocation() {
        return arrivalLocation;
    }

    public Date getDepartureTime() {
        return new Date(departureTime.getTime());
    }

    public Date getArrivalTime() {
        return new Date(arrivalTime.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarrierMovement that = (CarrierMovement) o;

        return sameValueAs(that);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.departureLocation)
                .append(this.departureTime).append(this.arrivalLocation)
                .append(this.arrivalTime).toHashCode();
    }

    private boolean sameValueAs(CarrierMovement other) {
        return other != null
                && new EqualsBuilder()
                .append(this.departureLocation, other.departureLocation)
                .append(this.departureTime, other.departureTime)
                .append(this.arrivalLocation, other.arrivalLocation)
                .append(this.arrivalTime, other.arrivalTime).isEquals();
    }
}
