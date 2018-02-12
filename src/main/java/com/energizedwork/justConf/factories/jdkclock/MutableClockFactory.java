package com.energizedwork.justConf.factories.jdkclock;

import com.energizedwork.justConf.factories.jdkclock.clocks.MutableClock;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Clock;

/**
 * Create a {@link MutableClock} based on the clock returned from the {@link #initialClockFactory}
 *
 * <p>the zoneId of this factory is ignored</p>
 */
@JsonTypeName("mutable")
public class MutableClockFactory extends ClockFactory {

    /**
     * provide the initial clock for the mutable clock
     */
    @NotNull
    @Valid
    public ClockFactory initialClockFactory;

    /**
     * create a mutable clock with the initial delegate clock provided by the initialClockFactory
     *
     * @return a {@link MutableClock}
     */
    @Override
    @JsonIgnore
    public Clock createClock() {
        return new MutableClock(initialClockFactory.createClock());
    }

}
