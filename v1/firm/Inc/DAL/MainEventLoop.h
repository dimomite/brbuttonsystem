#pragma once
#include "BtCtrl.h"
#include "ButtonsCtrl.h"
#include "EntertainmentCtrl.h"
#include "PlayersIndicatorCtrl.h"
#include "PreciseTimer.h"
#include "TouchControlsCtrl.h"
#include "UsbCtrl.h"

typedef struct
{
    BtCtrl_t *btCtrl;
    ButtonsCtrl_t *buttonsCtrl;
    EntertainmentCtrl_t *entCtrl;
    PlayersIndicatorCtrl_t *playersIndicatorCtrl;
    PreciseTimer_t *preciseTimer;
    TouchControlsCtrl_t *touchControlsCtrl;
    UsbCtrl_t *usbCtrl;
} MainEventLoop_t;

void mainEventLoop_init(MainEventLoop_t *el);

void mainEventLoop_start(MainEventLoop_t *el);
void mainEventLoop_tickEvent(MainEventLoop_t *el);
