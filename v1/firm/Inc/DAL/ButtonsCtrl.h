#pragma once

#include <stdint.h>

typedef struct ButtonsCtrl_t
{
    /**
     * Callback for buttons click.
     * @return  0 if no further callbacks are needed
     *          other othervise
     */
    uint8_t (*onButtonsClicked)(struct ButtonsCtrl_t *bc);
} ButtonsCtrl_t;

typedef struct
{
    /**
     * Buttons in expected order:
     * - red = bit 0
     * - green = bit 1
     * - yellow = bit 2
     * - blue = bit 3
     */
    uint8_t buttons;
} PressedButtons_t;

void buttons_pressedEvent(ButtonsCtrl_t *bc);

void buttons_enable(ButtonsCtrl_t *bc);
void buttons_disable(ButtonsCtrl_t *bc);

void buttons_getPressedButtons(ButtonsCtrl_t *bc, PressedButtons_t *buttons);
uint8_t buttons_isRedButtonPressed(const PressedButtons_t *buttons);
uint8_t buttons_isGreenButtonPressed(const PressedButtons_t *buttons);
uint8_t buttons_isYellowButtonPressed(const PressedButtons_t *buttons);
uint8_t buttons_isBlueButtonPressed(const PressedButtons_t *buttons);
