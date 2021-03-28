#include "DAL/MainEventLoop.h"
#include "DAL/PreciseTimer.h"

#include "DAL/TouchControlsCtrl.h"

#include "DeviceMacros.h"

#define IS_STATUS(s) ((status) & (1 << (s)))
#define SET_STATUS(s) do { status |= 1 << (s); } while (0)
#define CLEAR_STATUS(s) do { status &= ~(1 << (s)); } while (0)

#define STATUS_START_TIMER 0
#define STATUS_STOP_TIMER 1
#define STATUS_TIMER_DELAY 2
#define STATUS_TIMER_RUNNING 3

volatile static uint8_t lock;

static MainEventLoop_t *eventLoop; // TODO remove, just for BT debug

static uint8_t status = 0;

static int8_t timerLimit;
static int8_t timerTicksValue; // 0-127 ticks
static int16_t delayValue;
static int8_t timerSeconds;

static uint8_t temp;
static PressedButtons_t buttons;

static UsbHidInReport_t usbInReport;

static uint8_t onButtonPressed(ButtonsCtrl_t * bc) { // temp
    buttons_getPressedButtons(bc, &buttons);
    temp = 1;

    return 0;
}

static void onBtData(uint8_t data) {
    playersIndicator_displayPressedLed(eventLoop->playersIndicatorCtrl, data);
}

static void onUsbReport(UsbHidOutReport_t *outReport) {
    if (!outReport) return;
    if (outReport->reportId != 2) return; // 1 for IN reports, 2 for OUT

    playersIndicator_displayPressedLed(eventLoop->playersIndicatorCtrl, outReport->data[0]);
}

void mainEventLoop_init(MainEventLoop_t *el)
{
    eventLoop = el;
    el->buttonsCtrl->onButtonsClicked = onButtonPressed;
    el->btCtrl->onDataByteReceived = onBtData;
    el->usbCtrl->onReportReceived = onUsbReport;

    usbInReport.reportId = 1; // IN report id
}

void mainEventLoop_run(MainEventLoop_t *el)
{
    lock = 0;

    while (1)
    {
        while (lock)
        {
            // 128 ticks per second
            if (status)
            {
                if (IS_STATUS(STATUS_START_TIMER))
                {
                    timerTicksValue = 0;
                    timerSeconds = 0;

                    CLEAR_STATUS(STATUS_START_TIMER);
                }

                if (IS_STATUS(STATUS_STOP_TIMER))
                {
                    timerTicksValue = 0;
                    timerSeconds = 0;
                    CLEAR_STATUS(STATUS_TIMER_DELAY);
                    CLEAR_STATUS(STATUS_TIMER_RUNNING);
                    CLEAR_STATUS(STATUS_STOP_TIMER);
                }

                if (IS_STATUS(STATUS_TIMER_DELAY))
                {
                    if (0 == --delayValue)
                    {
                        CLEAR_STATUS(STATUS_TIMER_DELAY);
                        SET_STATUS(STATUS_TIMER_RUNNING);
                        if (el->preciseTimer->onTimerStarted)
                        {
                            el->preciseTimer->onTimerStarted(); // callback on timer start
                        }
                    }
                }

                if (IS_STATUS(STATUS_TIMER_RUNNING))
                {
                    if (++timerTicksValue < 0)
                    { // ticks are in [0...127], after - overflow to -128
                        timerTicksValue = 0;
                        if (++timerSeconds == timerLimit)
                        {
                            SET_STATUS(STATUS_STOP_TIMER);
                            if (el->preciseTimer->onTimerComplete)
                            {
                                el->preciseTimer->onTimerComplete(); // callback on timer complete
                            }
                        }
                        else
                        {
                            if (el->preciseTimer->onSecondUpdated)
                            {
                                el->preciseTimer->onSecondUpdated(timerTicksValue); // callback on each second
                            }
                        }
                    }
                }
            } // if (status)

            // send tick to sound and light control
            ent_onSystemTick(el->entCtrl);

            // send tick to bluetooth controller
            btctrl_onSystemTick(el->btCtrl);

            if (status)
            {
                debugLedOn();
                status = 0;
            }
            else
            {
                debugLedOff();
                status = 1 << 7;
            }

            if (temp) { // updates on button click
                playersIndicator_displayPressedLed(el->playersIndicatorCtrl, buttons.buttons);
                display_showPressedButtons(el->displayCtrl, &buttons);
                buttons_enable(el->buttonsCtrl);

                SoundProfile_t sound = {
                    .freq = ENT_FREQ_FALSE_START,
                    .duration = 32,
                    .repetitions = 5,
                };
                ent_startSound(el->entCtrl, &sound);

                LightProfile_t light = {
                    .intensity = 500,
                };
                ent_startLight(el->entCtrl, &light);

                usbInReport.data[0] = buttons.buttons;
                usbInReport.data[4] = 0x75;
                usbctrl_sendReport(el->usbCtrl, &usbInReport);

                temp = 0;
            }

            touchControl_onEnabledChanged(el->touchControlsCtrl, getTouchEnabledPinState());

            lock = 0;
        } // while (lock) {
    }
}

void mainEventLoop_tickEvent(MainEventLoop_t *el)
{
    lock = 1;
}

void preciseTimer_startPreciseTiming(PreciseTimer_t *pt, int8_t secondsLimit, int16_t delayTicks)
{
    CLEAR_STATUS(STATUS_STOP_TIMER);
    CLEAR_STATUS(STATUS_TIMER_RUNNING);
    timerLimit = secondsLimit;
    delayValue = delayTicks;
    SET_STATUS(STATUS_START_TIMER);
}

void preciseTimer_stopPreciseTiming(PreciseTimer_t *pt)
{
    CLEAR_STATUS(STATUS_START_TIMER);
    SET_STATUS(STATUS_START_TIMER);
}

void preciseTimer_getPreciseTiming(PreciseTimer_t *pt, PreciseTiming_t *timings)
{
    if (IS_STATUS(STATUS_TIMER_RUNNING))
    {
        timings->seconds = timerSeconds;
        uint16_t cnt = readMainTimerValue();
        timings->subticks = (int16_t)(timerTicksValue << 8) | (cnt >> 4);
    }
}
