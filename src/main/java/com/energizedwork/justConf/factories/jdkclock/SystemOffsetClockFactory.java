package com.energizedwork.justConf.factories.jdkclock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.Clock;
import java.time.Duration;

/**
 * Create a system Clock offset by the configured duration
 */
@JsonTypeName("system-offset")
public class SystemOffsetClockFactory extends ClockFactory {

    /**
     * the Duration to use to offset the system clock.
     *
     * <p>when null, a system clock will be returned with no offset</p>
     */
    public Duration offset;

    @Override
    @JsonIgnore
    public Clock createClock() {
        return offset == null ? Clock.system(getResovedZoneId()) : Clock.offset(Clock.system(getResovedZoneId()), offset);
    }

}
