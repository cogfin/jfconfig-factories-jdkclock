package com.energizedwork.justConf.factories.jdkclock

import java.time.Clock
import java.time.ZoneId

class ClockFactorySpec extends BaseClockFactorySpec {

    def "return a ZoneId in the default time zone when zoneId is not set"() {
        given:
            ZoneId minsk = ZoneId.of('Europe/Minsk')
            assert minsk != defaultTimeZone
            TimeZone.default = TimeZone.getTimeZone(minsk)

        when:
            ClockFactory clockFactory = new ConcreteClockFactory()

        then:
            clockFactory.resovedZoneId == minsk
    }

    def "return a the correct ZoneId when zoneId is supplied"() {
        given:
            String zone = 'Indian/Christmas'
            assert defaultTimeZone != TimeZone.getTimeZone(zone)
            ConcreteClockFactory clockFactory = new ConcreteClockFactory(zoneId: zone)

        expect:
            clockFactory.resovedZoneId.toString() == zone
    }

    static class ConcreteClockFactory extends ClockFactory {
        @Override
        Clock createClock() {
            return null
        }

    }
}
