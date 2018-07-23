package com.energizedwork.justConf.factories.jdkclock.clocks;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.TemporalAmount;

/**
 * A mutable clock suitable for testing
 *
 * <p><strong>This clock is not thread safe.</strong> To prevent significant performance impact this clock is not thread safe.</p>
 * <p>If any thread accesses this clock whilst the clock is being mutated the results are undefined</p>
 */
public class MutableClock extends Clock {

    private Clock originalClock;
    private Clock delegateClock;

    /**
     * @param originalClock the initial delegate clock which can be restored using the {@link #reset} method
     */
    public MutableClock(Clock originalClock) {
        this.originalClock = originalClock;
        delegateClock = originalClock;
    }

    /**
     * @return delegateClock.getZone()
     */
    @Override
    public ZoneId getZone() {
        return delegateClock.getZone();
    }

    /**
     * @return delegateClock.withZone(zoneId)
     */
    @Override
    public Clock withZone(ZoneId zoneId) {
        return delegateClock.withZone(zoneId);
    }

    /**
     * @return delegateClock.instant()
     */
    @Override
    public Instant instant() {
        return delegateClock.instant();
    }

    /**
     * @return delegateClock.millis()
     */
    @Override
    public long millis() {
        return delegateClock.millis();
    }

    /**
     * sets the delegate clock back to the original clock that this instance was created with
     */
    public void reset() {
        delegateClock = originalClock;
    }

    /**
     * set the delegate clock to an offset clock using the current delegate clock as a base and using the provided offset
     *
     * @param offset the amount of time to offset the current delegateClock
     */
    public void offset(Duration offset) {
        delegateClock = Clock.offset(delegateClock, offset);
    }

    /**
     * set the delegate clock to a fixed clock at the same time as the delegate clock plus the forwardAmount
     *
     * @param forwardAmount the amount of time added to the current delegateClock
     */
    public void fixForward(TemporalAmount forwardAmount) {
        delegateClock = Clock.fixed(instant().plus(forwardAmount), getZone());
    }

    /**
     * set the delegate clock to a fixed instant
     *
     * @param instant the time to set the delegate clock to
     */
    public void fix(Instant instant) {
        delegateClock = Clock.fixed(instant, getZone());
    }

    /**
     * set the delegate clock
     *
     * @param delegate the new delegate clock
     */
    public void setClock(Clock delegate) {
        delegateClock = delegate;
    }

}
