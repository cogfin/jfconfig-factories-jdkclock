package com.energizedwork.justConf.factories.jdkclock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.validation.Valid;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

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

    /**
     * use the rules in a fixable clock factory to calculate an offset from the current time.
     *
     * <p>When a fixable clock factory is configured, the offset property will be ignored</p>
     * <p>When the instant property of the fixable clock factory is null, a system clock will be returned with no offset</p>
     * <p>The zoneId of the fixable clock will be ignored, the zoneId of this offset factory will be used if required to convert
     * a local date to an instant (unless localTimeIsUtc is true)</p>
     */
    @Valid
    public FixableClockFactory fixableClockFactory;

    @Override
    @JsonIgnore
    public Clock createClock() {
        ZoneId zoneId = getResovedZoneId();
        if (fixableClockFactory != null && fixableClockFactory.instant != null) {
            Instant startTime = fixableClockFactory.getParsedInstant(zoneId);
            return Clock.offset(Clock.system(zoneId), Duration.between(Instant.now(), startTime));
        } else if (offset == null || fixableClockFactory != null) {
            return Clock.system(zoneId);
        } else {
            return Clock.offset(Clock.system(zoneId), offset);
        }
    }

}
