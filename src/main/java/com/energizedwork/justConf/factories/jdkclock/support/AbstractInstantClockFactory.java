package com.energizedwork.justConf.factories.jdkclock.support;

import com.energizedwork.justConf.factories.jdkclock.ClockFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;

/**
 * Provide a way to configure an instant in time
 *
 * <p>
 * The {@link #instant} will be parsed using the {@link #format} or {@link DateTimeFormatter#ISO_INSTANT} when format is null
 * </p>
 * <p>
 * The format can parse to an {@link Instant}, {@link LocalDateTime} or {@link LocalDate}. When converting a LocalDate to an Instant
 * the time will be set to midnight. LocalDateTime and LocalDate will use the configured time zone (or UTC when {@link #localTimeIsUtc}
 * is set to true) to convert the local time to an Instant.
 * </p>
 */
public abstract class AbstractInstantClockFactory extends ClockFactory {

    /**
     * the instant in time
     */
    public String instant;

    /**
     * the pattern to use to parse the instant, when null a {@link DateTimeFormatter#ISO_INSTANT} formatter will be used.
     *
     * <p>See &quot;Patterns for Formatting and Parsing&quot; in {@link DateTimeFormatter} for pattern formats</p>
     */
    public String format;

    /**
     * when true use UTC instead of the configured time zone to create the Instant when there is no timezone information
     */
    public boolean localTimeIsUtc;

    protected Instant getParsedInstant(ZoneId zoneId) {
        TemporalAccessor fixedTime;
        if (format == null) {
            fixedTime = DateTimeFormatter.ISO_INSTANT.parse(instant);
        } else {
            DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(format).toFormatter();
            fixedTime = formatter.parseBest(instant, Instant::from, LocalDateTime::from, LocalDate::from);
            if (fixedTime instanceof LocalDateTime) {
                fixedTime = ZonedDateTime.of((LocalDateTime)fixedTime, localTimeIsUtc ? ZoneId.of("UTC") : zoneId);
            } else if (fixedTime instanceof LocalDate) {
                fixedTime = ZonedDateTime.of((LocalDate)fixedTime, LocalTime.MIDNIGHT, localTimeIsUtc ? ZoneId.of("UTC") : zoneId);
            }
        }
        return Instant.from(fixedTime);
    }

}
