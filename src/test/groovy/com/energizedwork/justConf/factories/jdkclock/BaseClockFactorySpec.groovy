package com.energizedwork.justConf.factories.jdkclock

import spock.lang.Specification

import java.time.Clock
import java.time.ZoneId

abstract class BaseClockFactorySpec extends Specification {

    TimeZone defaultTimeZone
    long testTolerance = 2000

    def setup() {
        defaultTimeZone = TimeZone.default
    }

    def cleanup() {
        if (TimeZone.default != defaultTimeZone) {
            TimeZone.default = defaultTimeZone
        }
    }

}
