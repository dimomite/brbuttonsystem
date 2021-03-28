#include "DAL/DisplayCtrl.h"

#include "DeviceMacros.h"

#define SEGMENT_A (1 << 1)
#define SEGMENT_B (1 << 2)
#define SEGMENT_C (1 << 3)
#define SEGMENT_D (1 << 4)
#define SEGMENT_E (1 << 6)
#define SEGMENT_F (1 << 7)
#define SEGMENT_G (1 << 5)

#define VALUE_EMPTY (10)
#define VALUE_DASH (11)

static uint8_t numbers[] = {
    (SEGMENT_A | SEGMENT_B | SEGMENT_C | SEGMENT_D | SEGMENT_E | SEGMENT_F),             // 0
    (SEGMENT_B | SEGMENT_C),                                                             // 1
    (SEGMENT_A | SEGMENT_B | SEGMENT_D | SEGMENT_E | SEGMENT_G),                         // 2
    (SEGMENT_A | SEGMENT_B | SEGMENT_C | SEGMENT_D | SEGMENT_G),                         // 3
    (SEGMENT_B | SEGMENT_C | SEGMENT_F | SEGMENT_G),                                     // 4
    (SEGMENT_A | SEGMENT_C | SEGMENT_D | SEGMENT_F | SEGMENT_G),                         // 5
    (SEGMENT_A | SEGMENT_C | SEGMENT_D | SEGMENT_E | SEGMENT_F | SEGMENT_G),             // 6
    (SEGMENT_A | SEGMENT_B | SEGMENT_C),                                                 // 7
    (SEGMENT_A | SEGMENT_B | SEGMENT_C | SEGMENT_D | SEGMENT_E | SEGMENT_F | SEGMENT_G), // 8
    (SEGMENT_A | SEGMENT_B | SEGMENT_C | SEGMENT_D | SEGMENT_F | SEGMENT_G),             // 9
    (0),                                                                                 // empty - VALUE_EMPTY
    (SEGMENT_G),                                                                         // dash - VALUE_DASH
};

static void showState(DisplayCtrl_t *dc);

void display_init(DisplayCtrl_t *dc)
{
    clearFrontLcdResetPin();
}

void display_clearAll(DisplayCtrl_t *dc)
{
    dc->sec = 0;
    dc->dec = 0;
    dc->playersLeds = 0;
    showState(dc);
}

void display_clearGameTime(DisplayCtrl_t *dc)
{
    dc->sec = 0;
    dc->dec = 0;
    showState(dc);
}

void display_clearPlayers(DisplayCtrl_t *dc)
{
    dc->playersLeds = 0;
    showState(dc);
}

void display_showGameTime(DisplayCtrl_t *dc, uint8_t gameTimeSec)
{
    dc->sec = numbers[gameTimeSec % 10];
    gameTimeSec /= 10;
    // dc->dec = numbers[gameTimeSec > 0 ? gameTimeSec : VALUE_EMPTY];
    dc->dec = numbers[gameTimeSec];
    showState(dc);
}

void display_showTimeDash(DisplayCtrl_t *dc)
{
    dc->sec = numbers[VALUE_DASH];
    dc->dec = numbers[VALUE_DASH];
    showState(dc);
}

void display_showPressedButtons(DisplayCtrl_t *dc, const PressedButtons_t *buttons)
{
    dc->playersLeds = 0;
    if (buttons_isRedButtonPressed(buttons))
        dc->playersLeds |= 1 << 5;
    if (buttons_isGreenButtonPressed(buttons))
        dc->playersLeds |= 1 << 7;
    if (buttons_isYellowButtonPressed(buttons))
        dc->playersLeds |= 1 << 4;
    if (buttons_isBlueButtonPressed(buttons))
        dc->playersLeds |= 1 << 6;

    showState(dc);
}

void showState(DisplayCtrl_t *dc)
{
    clearFrontLcdCsPin();
    // writeSpi1Data(0);
    // writeSpi1Data(0xff);
    // writeSpi1Data(0);
    
    writeSpi1Data(dc->sec);
    writeSpi1Data(dc->dec);
    writeSpi1Data(dc->playersLeds);

    waitSpi1(); // TODO use postponded jobs and interrupts
    setFrontLcdCsPin();
}
