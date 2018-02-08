package com.energizedwork.justConf.factories.jdkclock;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.StringIdGenerator;
import io.dropwizard.jackson.Discoverable;

import java.time.Clock;
import java.time.ZoneId;

/**
 * Create a Clock with the configured time zone
 *
 * <p>The system default time zone will be used when zoneId is null</p>
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="type")
@JsonIdentityInfo(generator = StringIdGenerator.class, property = "id", scope = ClockFactory.class)
public abstract class ClockFactory implements Discoverable {

    /**
     * use to create references to this instance within configuration
     */
    public String id;

    /**
     * the zoneId for the Clock
     */
    public String zoneId;

    /**
     * a ZoneId created from the {@link #zoneId} or the system default if zoneId is null
     * @return {@link ZoneId#of(String)} or {@link ZoneId#systemDefault()} when zoneId is null
     */
    @JsonIgnore
    protected ZoneId getResovedZoneId() {
        return zoneId == null ? ZoneId.systemDefault() : ZoneId.of(zoneId);
    }

    /**
     * a Clock with the configured time zone
     * @return a Clock with the configured time zone
     */
    @JsonIgnore
    public abstract Clock createClock();

}
