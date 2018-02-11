package com.energizedwork.justConf.factories.jdkclock

import java.time.Clock
import java.time.Duration
import java.time.Instant

import static java.lang.System.currentTimeMillis
import static java.time.temporal.ChronoUnit.HOURS

class SystemOffsetClockFactorySpec extends BaseClockFactorySpec {

    def "return a system clock offset by the configured duration for the given timezone"() {
        given:
            String zone = 'Pacific/Easter'
            Duration sixHours = Duration.ofHours(6)
            long sixHoursInMillis = hoursToMillis(6)
            assert defaultTimeZone != TimeZone.getTimeZone(zone)
            Clock clock = new SystemOffsetClockFactory(zoneId: zone, offset: sixHours).createClock()

        expect:
            clock.zone.toString() == zone
            (clock.millis() - currentTimeMillis() - sixHoursInMillis).abs() < testTolerance
    }

    def "a null duration will return a system clock with no offset"() {
        given:
        String zone = 'Indian/Christmas'
        assert defaultTimeZone != TimeZone.getTimeZone(zone)
        Clock clock = new SystemOffsetClockFactory(zoneId: zone).createClock()

        expect:
        clock.zone.toString() == zone
        (clock.millis() - currentTimeMillis()).abs() < testTolerance
    }

    def "allow the offset duration to be calculated from a fixed point in time"() {
        given:
            def fiveHoursFromNow = Instant.now().plus(5, HOURS)
            long fiveHoursInMillis = hoursToMillis(5)
            Clock clock = new SystemOffsetClockFactory(instant: fiveHoursFromNow.toString()).createClock()

        expect:
            long millis = clock.millis()
            (millis - currentTimeMillis() - fiveHoursInMillis).abs() < testTolerance
            sleep(5)
            clock.millis() > millis
    }

    long hoursToMillis(int hours) {
        hours * 3600 * 1000;
    }

}
