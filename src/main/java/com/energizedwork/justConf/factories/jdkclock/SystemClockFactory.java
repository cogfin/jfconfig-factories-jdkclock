package com.energizedwork.justConf.factories.jdkclock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.Clock;

/**
 * Create a system Clock in the configured time zone
 */
@JsonTypeName("system")
public class SystemClockFactory extends ClockFactory {

    @Override
    public Clock createClock() {
        return Clock.system(getResovedZoneId());
    }

}
