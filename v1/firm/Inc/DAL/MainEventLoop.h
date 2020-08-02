#pragma once
#include "PreciseTimer.h"

typedef struct
{
    PreciseTimer_t *preciseTimer;
} MainEventLoop_t;

MainEventLoop_t mainEventLoopInstance; // main singleton

void mainEventLoop_start(MainEventLoop_t *el);
void mainEventLoop_tickEvent(MainEventLoop_t *el);
