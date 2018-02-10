package com.energizedwork.justConf.factories.jdkclock

import java.time.Clock
import java.time.Duration

import static java.lang.System.currentTimeMillis

class SystemOffsetClockFactorySpec extends BaseClockFactorySpec {

    def "return a system clock offset by the configured duration for the given timezone"() {
        given:
            String zone = 'Pacific/Easter'
            Duration sixHours = Duration.ofHours(6)
            long sixHoursInMillis = 6 * 3600 * 1000;
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

}
