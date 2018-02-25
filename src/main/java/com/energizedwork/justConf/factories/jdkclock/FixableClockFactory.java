package com.energizedwork.justConf.factories.jdkclock;

import com.energizedwork.justConf.factories.jdkclock.support.AbstractInstantClockFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.*;

/**
 * Create a fixed Clock set to the configured instant or a system clock when instant is null
 *
 * @see AbstractInstantClockFactory
 */
@JsonTypeName("fixable")
public class FixableClockFactory extends AbstractInstantClockFactory {

    @Override
    public Clock createClock() {
        ZoneId zoneId = getResovedZoneId();
        if (instant == null) {
            return Clock.system(zoneId);
        } else {
            return Clock.fixed(getParsedInstant(zoneId), zoneId);
        }
    }

}
