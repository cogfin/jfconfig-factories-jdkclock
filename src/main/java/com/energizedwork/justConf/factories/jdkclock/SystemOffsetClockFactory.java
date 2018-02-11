package com.energizedwork.justConf.factories.jdkclock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

/**
 * Create a system Clock offset by the configured duration, or calculated from the configured instant (start time)
 *
 * <p>When instant is provided, a system clock is returned which is set to the instant</p>
 * <p>When instant and offset are null a system clock is returned with no offset</p>
 *
 * @see AbstractInstantClockFactory
 */
@JsonTypeName("system-offset")
public class SystemOffsetClockFactory extends AbstractInstantClockFactory {

    /**
     * the Duration to use to offset the system clock, ignored when instant is set
     */
    public Duration offset;

    /**
     * create an offset system clock
     *
     * <p>
     * When instant and offset are null, a system clock is returned with no offset
     * </p>
     * <p>
     * When instant is provided, the offset is set so that the clock is initially set to the configured instant
     * </p>
     * <p>
     * When offset is provided and instant is null, an offset system clock is returned
     * </p>
     *
     * @return a system clock
     */
    @Override
    @JsonIgnore
    public Clock createClock() {
        ZoneId zoneId = getResovedZoneId();
        if (instant != null) {
            Instant startTime = getParsedInstant(zoneId);
            return Clock.offset(Clock.system(zoneId), Duration.between(Instant.now(), startTime));
        } else if (offset != null) {
            return Clock.offset(Clock.system(zoneId), offset);
        } else {
            return Clock.system(zoneId);
        }
    }

}
