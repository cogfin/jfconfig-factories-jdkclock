package com.energizedwork.justConf.factories.jdkclock;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.StringIdGenerator;
import io.dropwizard.jackson.Discoverable;

import java.time.Clock;
import java.time.ZoneId;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="type")
@JsonIdentityInfo(generator = StringIdGenerator.class, property = "id", scope = ClockFactory.class)
public abstract class ClockFactory implements Discoverable {

    public String id;

    public String zoneId;

    @JsonIgnore
    protected ZoneId getResovedZoneId() {
        return zoneId == null ? ZoneId.systemDefault() : ZoneId.of(zoneId);
    }

    @JsonIgnore
    public abstract Clock createClock();

}
