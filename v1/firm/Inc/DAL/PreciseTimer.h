#pragma once

#include <stdint.h>

typedef struct
{
    void (*onSecondUpdated)(int8_t seconds);
    void (*onTimerStarted)();
    void (*onTimerComplete)();
} PreciseTimer_t;

typedef struct
{
    /**
     * Max 127 seconds
     */
    int8_t seconds;

    /**
     * 25000 tickes per second
     */
    int16_t subticks;
} PreciseTiming_t;

void preciseTimer_startPreciseTiming(PreciseTimer_t *pt, int8_t secondsLimit, int16_t delayTicks);
void preciseTimer_stopPreciseTiming(PreciseTimer_t *pt);

/**
 * If precise timer is currently running - updates input timings argument with current values.
 * If not running - keeps unchanged.
 */
void preciseTimer_getPreciseTiming(PreciseTimer_t *pt, PreciseTiming_t *timings);
