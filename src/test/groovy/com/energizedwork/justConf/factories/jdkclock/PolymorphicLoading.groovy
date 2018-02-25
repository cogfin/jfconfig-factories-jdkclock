package com.energizedwork.justConf.factories.jdkclock

import com.energizedwork.justConf.JFConfig
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.Valid
import javax.validation.constraints.NotNull

class PolymorphicLoading extends Specification {

    @Unroll
    def "can load #expectedClockFactoryClass.simpleName using Discoverable polymorphism"() {
        expect:
        loadConfig(cfgFilename).clockFactory.getClass() == expectedClockFactoryClass

        where:
        cfgFilename     || expectedClockFactoryClass
        'system'        || SystemClockFactory
        'fixable'       || FixableClockFactory
        'system-offset' || SystemOffsetClockFactory
        'mutable'       || MutableClockFactory
    }

    ConfigWithClock loadConfig(String filename) {
        JFConfig.fromClasspath(ConfigWithClock, "${filename}.yml")
    }

    static class ConfigWithClock {

        @NotNull
        @Valid
        public ClockFactory clockFactory;

    }

}
