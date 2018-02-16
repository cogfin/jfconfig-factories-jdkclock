[![License](https://img.shields.io/badge/License-MIT-brightgreen.svg)](https://opensource.org/licenses/MIT) [![Javadocs](https://www.javadoc.io/badge/com.energizedwork/jfconfig-factories-jdkclock.svg)](https://www.javadoc.io/doc/com.energizedwork/jfconfig-factories-jdkclock) [![Maven](https://maven-badges.herokuapp.com/maven-central/com.energizedwork/jfconfig-factories-jdkclock/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.energizedwork/jfconfig-factories-jdkclock)

# JFConfig factories for the Java 8 Clock

Dropwizard Discoverable configuration factories that do not require, but can be used with [JFConfig](https://github.com/energizedwork/justConf)

All ClockFactory implementations will return a Clock with the time zone set by the zoneId configuration property.
When the zoneId property is null the clock will use the system default time zone.

## Javadoc
[Latest release](https://javadoc.io/doc/com.energizedwork/jfconfig-factories-jdkclock)

## Examples

### Use a system clock in the local time zone in production
###### config/env/production.yml
```yaml
clockFactory:
    type: system
```

### Use a system clock in a set time zone in production
###### config/env/production.yml
```yaml
clockFactory:
    type: system
    zoneId: UTC
```

### Use a fixable clock in qa environment

The FixableClockFactory is used to easily switch between a "normal" system clock and a fixed clock that can be used to test time dependent
functionality.

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

The configuration above is using [JFConfig](https://github.com/energizedwork/justConf) which in turn uses the apache StrSubstitutor to replace environment variables.
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
