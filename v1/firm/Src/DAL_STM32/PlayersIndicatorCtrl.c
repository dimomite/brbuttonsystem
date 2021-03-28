#include "PlayersIndicatorCtrl.h"

#include "DeviceMacros.h"

static uint8_t buttonsToBackLeds(const PressedButtons_t *buttons)
{
    uint8_t leds = 0;
    if (buttons_isRedButtonPressed(buttons))
        leds |= 1 << 6;
    if (buttons_isGreenButtonPressed(buttons))
        leds |= 1 << 7;
    if (buttons_isYellowButtonPressed(buttons))
        leds |= 1 << 4;
    if (buttons_isBlueButtonPressed(buttons))
        leds |= 1 << 5;

    return leds;
}

void playersIndicator_displayPressedLed(PlayersIndicatorCtrl_t *pi, const PressedButtons_t *buttons)
{
    clearBackLedCsPin();
    writeSpi1Data(buttonsToBackLeds(buttons));
    waitSpi1();
    setBackLedCsPin();
}

void playersIndicator_displayPressedButtons(PlayersIndicatorCtrl_t *pi, const PressedButtons_t *buttons)
{
}

void playersIndicator_displayPressedLedAndButtons(PlayersIndicatorCtrl_t *pi, const PressedButtons_t *buttons)
{
}
