#include "TouchControlsCtrl.h"

static void enableTouchControls(TouchControlsCtrl_t *tc);
static void disableTouchControls(TouchControlsCtrl_t *tc);

void touchControl_onEnabledChanged(TouchControlsCtrl_t *tc, uint32_t pinState)
{
    if (pinState)
    { // pin is set - high level - feature should be disabled
        if (tc->state)
        {
            disableTouchControls(tc);
        }
    }
    else
    {
        if (0 == tc->state)
        {
            enableTouchControls(tc);
        }
    }
}

void enableTouchControls(TouchControlsCtrl_t *tc)
{
    tc->state = 1;
}

void disableTouchControls(TouchControlsCtrl_t *tc)
{
    tc->state = 0;
}
