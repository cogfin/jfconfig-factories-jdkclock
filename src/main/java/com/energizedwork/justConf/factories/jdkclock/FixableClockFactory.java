package com.energizedwork.justConf.factories.jdkclock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;

@JsonTypeName("fixable")
public class FixableClockFactory extends ClockFactory {

    public String instant;

    public String format;

    public boolean localTimeIsUtc;

    @Override
    @JsonIgnore
    public Clock createClock() {
        ZoneId zoneId = getResovedZoneId();
        if (instant == null) {
            return Clock.system(zoneId);
        } else {
            TemporalAccessor fixedTime = null;
            if (format == null) {
                fixedTime = DateTimeFormatter.ISO_INSTANT.parse(instant);
            } else {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(format).toFormatter();//.withZone(ZoneId.of("UTC"));
                fixedTime = formatter.parseBest(instant, Instant::from, LocalDateTime::from, LocalDate::from);
                if (fixedTime instanceof LocalDateTime) {
                    fixedTime = ZonedDateTime.of((LocalDateTime)fixedTime, localTimeIsUtc ? ZoneId.of("UTC") : zoneId);
                } else if (fixedTime instanceof LocalDate) {
                    fixedTime = ZonedDateTime.of((LocalDate)fixedTime, LocalTime.MIDNIGHT, localTimeIsUtc ? ZoneId.of("UTC") : zoneId);
                }
            }
            return Clock.fixed(Instant.from(fixedTime), zoneId);
        }
    }

}
