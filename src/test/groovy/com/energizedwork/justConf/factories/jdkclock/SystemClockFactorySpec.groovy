package com.energizedwork.justConf.factories.jdkclock

import spock.lang.Specification

import java.time.Clock
import java.time.ZoneId

import static java.lang.System.currentTimeMillis

class SystemClockFactorySpec extends BaseClockFactorySpec {

    def "return a system clock with the correct time zone when zoneId is supplied"() {
        given:
            String zone = 'Pacific/Easter'
            assert defaultTimeZone != TimeZone.getTimeZone(zone)
            Clock clock = new SystemClockFactory(zoneId: zone).createClock()

        expect:
            clock.zone.toString() == zone
            (clock.millis() - currentTimeMillis()).abs() < testTolerance
    }

}
