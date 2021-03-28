#pragma once

#include <stdint.h>

#include "ButtonsCtrl.h"

typedef struct
{
    uint8_t backIndicatorState;
    uint8_t buttonsIndicatorState;
} PlayersIndicatorCtrl_t;

void playersIndicator_displayPressedLed(PlayersIndicatorCtrl_t *pi, const PressedButtons_t *buttons);
void playersIndicator_displayPressedButtons(PlayersIndicatorCtrl_t *pi, const PressedButtons_t *buttons);
void playersIndicator_displayPressedLedAndButtons(PlayersIndicatorCtrl_t *pi, const PressedButtons_t *buttons);
