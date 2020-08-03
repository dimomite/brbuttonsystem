#pragma once
#include "ButtonsCtrl.h"
#include "PlayersIndicatorCtrl.h"
#include "PreciseTimer.h"
#include "TouchControlsCtrl.h"

typedef struct
{
    ButtonsCtrl_t *buttonsCtrl;
    PlayersIndicatorCtrl_t * playersIndicatorCtrl;
    PreciseTimer_t *preciseTimer;
    TouchControlsCtrl_t *touchControlsCtrl;
} MainEventLoop_t;

void mainEventLoop_init(MainEventLoop_t *el);

void mainEventLoop_start(MainEventLoop_t *el);
void mainEventLoop_tickEvent(MainEventLoop_t *el);
