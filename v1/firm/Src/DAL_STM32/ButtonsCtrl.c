#include "ButtonsCtrl.h"

#include "DeviceMacros.h"

void buttons_pressedEvent(ButtonsCtrl_t *bc)
{
    if (bc->onButtonsClicked) {
        if (bc->onButtonsClicked(bc)) {
            enableButtonInterrupts();
        } else { 
            disableButtonInterrupts();
        }
    }
}

void buttons_enable(ButtonsCtrl_t *bc)
{
    enableButtonInterrupts();
}

void buttons_disable(ButtonsCtrl_t *bc)
{
    disableButtonInterrupts();
}

void buttons_getPressedButtons(ButtonsCtrl_t *bc, PressedButtons_t *buttons)
{
    uint32_t b = readButtonsPressedIt();
    resetButtonsPressedIt();
    // TODO pins to player mapping should happen here
    buttons->buttons = (b >> 12) & 0x0fU;
}
