package com.energizedwork.justConf.factories.jdkclock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.Clock;

@JsonTypeName("system")
public class SystemClockFactory extends ClockFactory {

    @Override
    @JsonIgnore
    public Clock createClock() {
        return Clock.system(getResovedZoneId());
    }

}
