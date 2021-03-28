#pragma once

#include <stdint.h>
#include "ButtonsCtrl.h"

typedef struct
{
    uint8_t sec;
    uint8_t dec;
    uint8_t playersLeds;
} DisplayCtrl_t;

void display_init(DisplayCtrl_t *dc);
void display_clearAll(DisplayCtrl_t *dc);
void display_clearGameTime(DisplayCtrl_t *dc);
void display_clearPlayers(DisplayCtrl_t *dc);
void display_showGameTime(DisplayCtrl_t *dc, uint8_t gameTimeSec);
void display_showTimeDash(DisplayCtrl_t *dc);
void display_showPressedButtons(DisplayCtrl_t *dc, const PressedButtons_t *buttons);
