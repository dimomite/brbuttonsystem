#pragma once

#include <stdint.h>

typedef struct
{
    uint8_t state;

    void (*onTouchControlEvent)(uint8_t buttons);
} TouchControlsCtrl_t;

void touchControl_onEnabledChanged(TouchControlsCtrl_t *tc, uint32_t pinState);
