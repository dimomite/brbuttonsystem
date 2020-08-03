#pragma once

#include <stdint.h>

typedef struct
{
    uint8_t backIndicatorState;
    uint8_t buttonsIndicatorState;
} PlayersIndicatorCtrl_t;

void playersIndicator_displayPressedLed(PlayersIndicatorCtrl_t *pi, uint8_t players);
void playersIndicator_displayPressedButtons(PlayersIndicatorCtrl_t *pi, uint8_t players);
void playersIndicator_displayPressedLedAndButtons(PlayersIndicatorCtrl_t *pi, uint8_t players);
