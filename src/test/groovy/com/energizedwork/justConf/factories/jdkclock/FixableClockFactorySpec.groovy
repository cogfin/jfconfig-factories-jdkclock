package com.energizedwork.justConf.factories.jdkclock

import spock.lang.Unroll

import java.time.Clock
import java.time.ZoneId
import java.time.ZonedDateTime

import static java.lang.System.currentTimeMillis
import static java.time.temporal.ChronoField.*

class FixableClockFactorySpec extends BaseClockFactorySpec {

    @Unroll
    def "return a fixed clock for timezone #zone, format #format, instant #instant"() {
        given:
            assert defaultTimeZone != TimeZone.getTimeZone(zone)
            Clock clock = new FixableClockFactory(zoneId: zone, instant: instant, format: format, localTimeIsUtc: localTimeIsUtc).createClock()
            ZonedDateTime clockTime = ZonedDateTime.now(clock).withZoneSameInstant(ZoneId.of('UTC'))

        expect:
            clock.zone.toString() == zone
            clockTime.get(YEAR) == year
            clockTime.get(MONTH_OF_YEAR) == month
            clockTime.get(DAY_OF_MONTH) == day
            clockTime.get(HOUR_OF_DAY) == hour
            clockTime.get(MINUTE_OF_HOUR) == minute
            clockTime.get(SECOND_OF_MINUTE) == second
            clockTime.get(NANO_OF_SECOND) == nanos

        where:
            localTimeIsUtc | zone               | format                            | instant                             || year | month | day | hour | minute | second | nanos
            false          | 'Indian/Reunion'   | null                              | '2011-12-03T10:15:30Z'              || 2011 | 12    | 3   | 10   | 15     | 30     | 0
            false          | 'Indian/Reunion'   | null                              | '2011-12-03T10:15:30.333Z'          || 2011 | 12    | 3   | 10   | 15     | 30     | 333000000
            false          | 'Indian/Christmas' | 'yyyy-MM-dd HH:mm'                | '2017-11-10 09:08'                  || 2017 | 11    | 10  | 2    | 8      | 0      | 0
            true           | 'Indian/Christmas' | 'yyyy-MM-dd HH:mm'                | '2017-11-10 09:08'                  || 2017 | 11    | 10  | 9    | 8      | 0      | 0
            true           | 'Indian/Christmas' | 'yyyy-MM-dd HH'                   | '2017-11-10 09'                     || 2017 | 11    | 10  | 9    | 0      | 0      | 0
            false          | 'Pacific/Easter'   | 'yyyy-MM-dd HH:mm:ss.SSSSSSSSS x' | '2017-11-10 09:08:07.000000001 +02' || 2017 | 11    | 10  | 7    | 8      | 7      | 1
            true           | 'Pacific/Easter'   | 'yyyy-MM-dd HH:mm:ss.SSSSSSSSS x' | '2017-11-10 09:08:07.000000001 +02' || 2017 | 11    | 10  | 7    | 8      | 7      | 1
            false          | 'Pacific/Easter'   | 'yyyy-MM-dd HH:mm:ss.SSSSSSSSS'   | '2017-11-10 09:08:07.000000001'     || 2017 | 11    | 10  | 14   | 8      | 7      | 1
            false          | 'Pacific/Easter'   | 'yyyy-MM-dd'                      | '2018-02-06'                        || 2018 | 2     | 6   | 5    | 0      | 0      | 0
            true           | 'Pacific/Easter'   | 'yyyy-MM-dd'                      | '2018-02-06'                        || 2018 | 2     | 6   | 0    | 0      | 0      | 0
    }

    def "return a system clock for the given timezone when the instant is not set"() {
        given:
            String zone = 'Pacific/Easter'
            assert defaultTimeZone != TimeZone.getTimeZone(zone)
            Clock clock = new FixableClockFactory(zoneId: zone).createClock()

        expect:
            clock.zone.toString() == zone
            long millis = clock.millis()
            (millis - currentTimeMillis()).abs() < testTolerance
            sleep(5)
            clock.millis() > millis
    }

}
