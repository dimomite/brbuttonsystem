#include "PlayersIndicatorCtrl.h"

#include "DeviceMacros.h"

void playersIndicator_displayPressedLed(PlayersIndicatorCtrl_t *pi, uint8_t players)
{
    clearBackLedCsPin();
    writeSpi1Data(players << 4);
    setBackLedCsPin();
}

void playersIndicator_displayPressedButtons(PlayersIndicatorCtrl_t *pi, uint8_t players)
{
}

void playersIndicator_displayPressedLedAndButtons(PlayersIndicatorCtrl_t *pi, uint8_t players)
{
}
