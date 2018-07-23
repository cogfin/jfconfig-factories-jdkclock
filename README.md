[![License](https://img.shields.io/badge/License-MIT-brightgreen.svg)](https://opensource.org/licenses/MIT) [![Javadocs](https://www.javadoc.io/badge/uk.cogfin/jfconfig-factories-jdkclock.svg)](https://www.javadoc.io/doc/uk.cogfin/jfconfig-factories-jdkclock) [![Maven](https://maven-badges.herokuapp.com/maven-central/uk.cogfin/jfconfig-factories-jdkclock/badge.svg)](https://maven-badges.herokuapp.com/maven-central/uk.cogfin/jfconfig-factories-jdkclock)

# JFConfig factories for the Java 8 Clock

Dropwizard Discoverable configuration factories that do not require, but can be used with [JFConfig](https://github.com/cogfin/jfconfig)

All ClockFactory implementations will return a Clock with the time zone set by the zoneId configuration property.
When the zoneId property is null the clock will use the system default time zone.

## Javadoc
[Latest release](https://javadoc.io/doc/uk.cogfin/jfconfig-factories-jdkclock)

## Examples

### System clock (type: system)

#### Use a system clock in the local time zone in production
###### config/env/production.yml
```yaml
clockFactory:
    type: system
```

#### Use a system clock in a set time zone in production
###### config/env/production.yml
```yaml
clockFactory:
    type: system
    zoneId: UTC
```

### Clocks for testing

#### Fixable clock (type: fixable)

The FixableClockFactory is used to easily switch between a "normal" system clock and a fixed clock

When environemt variable FIX_TIME is not set, the clockFactory will return a normal system clock in the Paris time zone
Setting FIX_TIME to 2018-02-08 08:05 will fix the clock to that local time (2018-02-08 07:05 UTC)

###### config/env/qa.yml
```yaml
clockFactory:
    type: fixable
    instant: ${FIX_TIME:-}
    format: yyyy-MM-dd HH:mm
    zoneId: Europe/Paris
```

The configuration above is using [JFConfig](https://github.com/cogfin/jfconfig) which in turn uses the apache StrSubstitutor to replace environment variables.
The ${FIX_TIME:-} string will be replaced by the FIX_TIME environment variable when it is present, or an empty string (null) when not.
To set an empty string in yaml use ${ENV_VAR_NAME:-""} or ${ENV_VAR_NAME:-''} 

Completely flexible configuration
###### config/env/qa.yml

```yaml
clockFactory:
    type: fixable
    instant: ${FIX_TIME:-}
    format: ${FIX_TIME_FORMAT:-yyyy-MM-dd HH:mm}
    zoneId: ${FIX_TIME_ZONE:-}
```

#### System offset (type: system-offset)

Return a system clock offset by the configured duration.

When an instant is configured (as above for the fixable factory) the offset is calculated as the difference between the instant and the system clock.
This means that the clock returned will initially be set to the configured instant and then keep ticking.

When instant is configured the duration is ignored.

##### A system clock offset by 48 hours
###### config/env/demo.yml
```yaml
clockFactory:
    type: system-offset
    offset: P2D
```

##### A system clock which when requested is initially offset to 2018-06-30 02:55 UTC 
###### config/env/demo.yml
```yaml
clockFactory:
    type: system-offset
    instant: 2018-06-30 03:55
    format: yyyy-MM-dd HH:mm
    zoneId: Europe/London
```

Use environment variables to choose between a system clock (OFFSET_DURATION and INITIAL_TIME both unset or empty), a clock offset by a duration, or a clock which is initially set to a specific instant in time
###### config/env/demo.yml
```yaml
clockFactory:
    type: system-offset
    offset: ${OFFSET_DURATION:-}
    instant: ${INITIAL_TIME:-}
    format: ${INITIAL_TIME_FORMAT:-yyyy-MM-dd HH:mm}
    zoneId: ${TIME_ZONE:-}
```

#### Mutable clock (type: mutable)

The [MutableClock](https://javadoc.io/page/uk.cogfin/jfconfig-factories-jdkclock/latest/com/energizedwork/justConf/factories/jdkclock/clocks/MutableClock.html) returned from this factory delegates all calls to an underlying clock which can be swapped out using methods on the MutableClock.

The initial delegate clock is set by providing a ClockFactory and can be restored by calling reset() on the MutableClock.

```yaml
clockFactory:
    type: mutable
      initialClockFactory:
        type: system
        zoneId: UTC
```
