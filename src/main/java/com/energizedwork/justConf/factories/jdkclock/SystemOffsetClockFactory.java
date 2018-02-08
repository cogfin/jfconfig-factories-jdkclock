package com.energizedwork.justConf.factories.jdkclock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.validation.constraints.NotNull;
import java.time.Clock;
import java.time.Duration;

/**
 * Create a system Clock offset by the configured duration
 */
@JsonTypeName("system-offset")
public class SystemOffsetClockFactory extends ClockFactory {

    /**
     * the Duration to use to offset the system clock
     */
    @NotNull
    public Duration offset;

    @Override
    @JsonIgnore
    public Clock createClock() {
        return Clock.offset(Clock.system(getResovedZoneId()), offset);
    }

}
