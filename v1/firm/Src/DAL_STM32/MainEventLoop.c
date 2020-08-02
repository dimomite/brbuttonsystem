#include "DAL/MainEventLoop.h"
#include "DAL/PreciseTimer.h"

#include "DeviceMacros.h"

#define IS_STATUS(s) ((status) & (1 << (s)))
#define SET_STATUS(s) do { status |= 1 << (s); } while (0)
#define CLEAR_STATUS(s) do { status &= ~(1 << (s)); } while (0)

#define STATUS_START_TIMER      0
#define STATUS_STOP_TIMER       1
#define STATUS_TIMER_DELAY      2
#define STATUS_TIMER_RUNNING    3

volatile static uint8_t lock;

static uint8_t status = 0;

static int8_t timerLimit;
static int8_t timerTicksValue; // 0-127 ticks
static int16_t delayValue;
static int8_t timerSeconds;

void mainEventLoop_start(MainEventLoop_t *el)
{
    lock = 0;

    while (1)
    {
        while (lock) {
            // 128 ticks per second
            if (status) {
                if (IS_STATUS(STATUS_START_TIMER)) {
                    timerTicksValue = 0;
                    timerSeconds = 0;

                    CLEAR_STATUS(STATUS_START_TIMER);
                }

                if (IS_STATUS(STATUS_STOP_TIMER)) {
                    timerTicksValue = 0;
                    timerSeconds = 0;
                    CLEAR_STATUS(STATUS_TIMER_DELAY);
                    CLEAR_STATUS(STATUS_TIMER_RUNNING);
                    CLEAR_STATUS(STATUS_STOP_TIMER);
                }

                if (IS_STATUS(STATUS_TIMER_DELAY)) {
                    if (0 == --delayValue) {
                        CLEAR_STATUS(STATUS_TIMER_DELAY);
                        SET_STATUS(STATUS_TIMER_RUNNING);
                        if (el->preciseTimer->onTimerStarted) {
                            el->preciseTimer->onTimerStarted(); // callback on timer start
                        }
                    }
                }

                if (IS_STATUS(STATUS_TIMER_RUNNING)) {
                    if (++timerTicksValue < 0) { // ticks are in [0...127], after - overflow to -128
                        timerTicksValue = 0;
                        if (++timerSeconds == timerLimit) {
                            SET_STATUS(STATUS_STOP_TIMER);
                            if (el->preciseTimer->onTimerComplete) {
                                el->preciseTimer->onTimerComplete(); // callback on timer complete
                            }
                        } else {
                            if (el->preciseTimer->onSecondUpdated) {
                                el->preciseTimer->onSecondUpdated(timerTicksValue); // callback on each second
                            }
                        }
                    }

                }
            } // if (status)

            if (status) {
                debugLedOn();
                status = 0;
            } else {
                debugLedOff();
                status = 1 << 7;
            }

            lock = 0;
        } // while (lock) {
    }
}

void mainEventLoop_tickEvent(MainEventLoop_t *el)
{
    lock = 1;
}


void preciseTimer_startPreciseTiming(PreciseTimer_t* pt, int8_t secondsLimit, int16_t delayTicks)
{
    CLEAR_STATUS(STATUS_STOP_TIMER);
    CLEAR_STATUS(STATUS_TIMER_RUNNING);
    timerLimit = secondsLimit;
    delayValue = delayTicks;
    SET_STATUS(STATUS_START_TIMER);
}

void preciseTimer_stopPreciseTiming(PreciseTimer_t* pt)
{
    CLEAR_STATUS(STATUS_START_TIMER);
    SET_STATUS(STATUS_START_TIMER);
}

void preciseTimer_getPreciseTiming(PreciseTimer_t *pt, PreciseTiming_t *timings)
{
    if (IS_STATUS(STATUS_TIMER_RUNNING)) {
        timings->seconds = timerSeconds;
        uint16_t cnt = readMainTimerValue();
        timings->subticks = (int16_t)(timerTicksValue << 8) | (cnt >> 4);
    }
}
